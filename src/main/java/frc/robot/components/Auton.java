package frc.robot.components;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import frc.robot.Robot;
import frc.robot.auto.Sequence;
import frc.robot.auto.commands.*;
import frc.robot.auto.vision.TargetType;
import frc.robot.util.states.*;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.robot.CommandQueue;
import frc.team5431.titan.core.robot.Component;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class Auton extends Component<Robot> {

	// Define Sequences for general usage
	private CommandQueue<Robot> sequenceCommands;
	private CommandQueue<Robot> drivebaseCommands;

	// // Predefined Sequences via button presses on operator controller buttons
	// private HashMap<Xbox.Button, Sequence> operatorSequences;

	// Define Sequence Commands for Components
	private EnumMap<Sequence, Function<Robot, List<Command<Robot>>>> shooterSequences;
	private EnumMap<Sequence, Function<Robot, List<Command<Robot>>>> climberSequences;

	// For mimic to know which sequence is running
	private Sequence runningSequence = null;

	public Auton() {
		// Initalize Objects

		// Command Queues
		sequenceCommands = new CommandQueue<>();
		drivebaseCommands = new CommandQueue<>();

		// Sequences to fire Shooter
		shooterSequences = new EnumMap<>(Sequence.class);
		
		// Sequences to control Climber
		climberSequences = new EnumMap<>(Sequence.class);

		// Add Shooter functions to Sequences
		{
			final Function<Robot, List<Command<Robot>>> stopEverythingIntake = (robot) -> {
				List<Command<Robot>> throwawayCommands = new ArrayList<>();

				throwawayCommands.add(new FlywheelCommand(FlywheelState.STOP));
				throwawayCommands.add(new IntakeCommand(IntakeState.STOP));
				throwawayCommands.add(new FeederCommand(FeederState.STOP));

				return throwawayCommands;
			};

			final Function<Robot, List<Command<Robot>>> startIntakeNoFlywheel = (robot) -> {
				List<Command<Robot>> throwawayCommands = new ArrayList<>();

				throwawayCommands.add(new FlywheelCommand(FlywheelState.STOP));
				throwawayCommands.add(new IntakeCommand(IntakeState.FULL));
				throwawayCommands.add(new FeederCommand(FeederState.FULL));

				return throwawayCommands;
			};

			final Function<Robot, List<Command<Robot>>> startIntakeAndFlywheel = (robot) -> {
				List<Command<Robot>> throwawayCommands = new ArrayList<>();

				throwawayCommands.add(new FlywheelCommand(FlywheelState.FULL));
				throwawayCommands.add(new IntakeCommand(IntakeState.FULL));
				throwawayCommands.add(new FeederCommand(FeederState.FULL));

				return throwawayCommands;
			};

			final Function<Robot, List<Command<Robot>>> targetShieldGenerator = (robot) -> {
				List<Command<Robot>> throwawayCommands = new ArrayList<>();

				throwawayCommands.add(new FlywheelCommand(FlywheelState.STOP));
				throwawayCommands.add(new IntakeCommand(IntakeState.STOP));
				throwawayCommands.add(new FeederCommand(FeederState.STOP));
				throwawayCommands.add(new Targetor(TargetType.UPPERPORT));

				return throwawayCommands;
			};

			shooterSequences.put(Sequence.STOPINTAKE, stopEverythingIntake);
			shooterSequences.put(Sequence.STARTINTAKE_NOFLYWHEEL, startIntakeNoFlywheel);
			shooterSequences.put(Sequence.STARTINTAKE_FLYWHEEL, startIntakeAndFlywheel);
		}

		// Add Climber functions to Sequences
		{
			final Function<Robot, List<Command<Robot>>> raiseClimber = (robot) -> {
				List<Command<Robot>> throwawayCommands = new ArrayList<>();

				throwawayCommands.add(new FeederCommand(FeederState.STOP));

				return throwawayCommands;
			};

			final Function<Robot, List<Command<Robot>>> lowerClimber = (robot) -> {
				List<Command<Robot>> throwawayCommands = new ArrayList<>();

				throwawayCommands.add(new FeederCommand(FeederState.FULL));

				return throwawayCommands;
			};

			climberSequences.put(Sequence.RAISECLIMBER, raiseClimber);
			climberSequences.put(Sequence.LOWERCLIMBER, lowerClimber);
		}
	}

	/**
	 * Component Init is used to reset all sensors
	 */
	@Override
	public void init(final Robot robot) {
		switch (robot.getMode()) {
		case AUTO:
			robot.getDrivebase().resetSensors();
			drivebaseCommands.clear();
			break;
		case TEST:
			robot.getDrivebase().resetSensors();
			break;
		case TELEOP:
			break;
		case DISABLED:
			break;
		default:
			abort(robot);
			break;
		}

		drivebaseCommands.clear();
		sequenceCommands.clear();
	}

	@Override
	public void periodic(final Robot robot) {
	}

	@Override
	public void disabled(final Robot robot) {
		abort(robot);
	}

	public void abort(final Robot robot) {
		sequenceCommands.done(robot);
		sequenceCommands.clear();

		drivebaseCommands.done(robot);
		drivebaseCommands.clear();

		robot.getDrivebase().setControlMode(ComponentControlMode.MANUAL);
		robot.getElevator().setControlMode(ComponentControlMode.MANUAL);
		robot.getIntake().setControlMode(ComponentControlMode.MANUAL);
		robot.getFeeder().setControlMode(ComponentControlMode.MANUAL);
		robot.getFlywheel().setControlMode(ComponentControlMode.MANUAL);
	}

	public CommandQueue<Robot> getSequenceCommands() {
		return sequenceCommands;
	}

	public CommandQueue<Robot> getDrivebaseCommands() {
		return drivebaseCommands;
	}
}
