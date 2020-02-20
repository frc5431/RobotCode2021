package frc.robot.components;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import frc.robot.Robot;
import frc.robot.auto.Routine;
import frc.robot.auto.Sequence;
import frc.robot.auto.SequenceMode;
import frc.robot.auto.commands.*;
import frc.robot.auto.vision.TargetType;
import frc.robot.util.states.*;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.robot.CommandQueue;
import frc.team5431.titan.core.robot.Component;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 * 
 * @author Liav Turkia. Code based off of 2019 Robot so Liav is added
 */
public class Auton extends Component<Robot> {

	// Define Sequences for general usage
	private CommandQueue<Robot> sequenceCommands;
	private CommandQueue<Robot> drivebaseCommands;

	// // Predefined Sequences via button presses on operator controller buttons
	// private HashMap<Xbox.Button, Sequence> operatorSequences;

	/**
	 * Define Sequence Commands for Components.
	 * 
	 * Sequence Enum, Reference to function that will be called to generate list of
	 * commands.
	 * 
	 * So getting a list of commands you should run
	 * "exampleSequences.get(Sequence.EXAMPLE);"
	 */
	private EnumMap<Sequence, Function<Robot, List<Command<Robot>>>> shooterSequences;
	private EnumMap<Sequence, Function<Robot, List<Command<Robot>>>> climberSequences;

	// For mimic to know which sequence is running
	private Sequence runningSequence = null;

	private boolean mimicLoaded = false;

	public Auton() {
		// Initalize Objects

		/*
		 * What is a command queue????!?!?!?!?!??!?!??!
		 * 
		 * Well a command queue, is plays back a list of commands.
		 */

		// Command Queues
		sequenceCommands = new CommandQueue<>();
		drivebaseCommands = new CommandQueue<>();

		/*
		 * initialize the sequences map so you can generate a list of commands and bind
		 * them to a sequence enum which is defined in sequence.java
		 */
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
				throwawayCommands.add(new Targetor(TargetType.SHIELDGENERATOR));

				return throwawayCommands;
			};

			shooterSequences.put(Sequence.STOPINTAKE, stopEverythingIntake);
			shooterSequences.put(Sequence.STARTINTAKE_NOFLYWHEEL, startIntakeNoFlywheel);
			shooterSequences.put(Sequence.STARTINTAKE_FLYWHEEL, startIntakeAndFlywheel);
			shooterSequences.put(Sequence.TARGET_SHIELDGENERATOR, targetShieldGenerator);
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

	/**
	 * Continue running the commands until all commands are complete
	 */
	@Override
	public void periodic(final Robot robot) {
		if (sequenceCommands.isEmpty()) {
			runningSequence = null;
		}

		sequenceCommands.update(robot);
		drivebaseCommands.update(robot);
	}

	@Override
	public void disabled(final Robot robot) {
		abort(robot);
	}

	public void runSequence(final Robot robot, final SequenceMode type, final Sequence seq) {
		sequenceCommands.done(robot);
		sequenceCommands.clear();
		runningSequence = seq;
		final Function<Robot, List<Command<Robot>>> selected = (type == SequenceMode.SHOOT ? shooterSequences
				: climberSequences).getOrDefault(seq, (rob) -> List.of());

		// Do any processing if needed
		sequenceCommands.addAll(selected.apply(robot));

		sequenceCommands.init(robot);
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

	public Sequence getRunningSequence() {
		return runningSequence;
	}

	public void loadMimic(final Routine r, final long delay) {
		if (r == null || !mimicLoaded) {
			return;
		}

		new Thread(() -> {
			mimicLoaded = false;
			Logger.l("Preloading auto routine: " + r.toString());
			// preloadedAutoCommands.clear();
			// if(delay > 0){
			// preloadedAutoCommands.add(new WaitCommand<>(delay));
			// }
			// final Path startHatchPath = r.getStartHatchPath();
			// if(startHatchPath != null){
			// final Sequence firstSequence = r.getFirstSequence();
			// preloadedAutoCommands.add(new Titan.ConsumerCommand<>((rob)->{
			// rob.getVision().setTargetType(r.isSwapped() ? TargetType.FRONT_RIGHT :
			// TargetType.FRONT_LEFT);
			// }));
			// preloadedAutoCommands.addAll(startHatchPath.generate(firstSequence,
			// r.isSwapped()));
			// if(firstSequence != null){
			// preloadedAutoCommands.add(new
			// Titan.ConditionalCommand<>((rob)->!isRunningSequence()));
			// preloadedAutoCommands.addAll(getAutoAim(null, Sequence.OUTTAKE, r.isSwapped()
			// ? TargetType.FRONT_RIGHT : TargetType.FRONT_LEFT));
			// preloadedAutoCommands.add(new Titan.WaitCommand<>(500));
			// }
			// }

			// final Path loadingStationPath = r.getLoadingStationPath();
			// if(loadingStationPath != null){
			// preloadedAutoCommands.addAll(loadingStationPath.generate(Sequence.LOADING_STATION,
			// r.isSwapped()));
			// preloadedAutoCommands.add(new
			// Titan.ConditionalCommand<>((rob)->!isRunningSequence()));
			// preloadedAutoCommands.addAll(getAutoAim(null, Sequence.INTAKE,
			// TargetType.FRONT_RIGHT));
			// }

			// final Path secondHatchPath = r.getSecondHatchPath();
			// if(secondHatchPath != null){
			// final Sequence secondSequence = r.getSecondSequence();
			// preloadedAutoCommands.addAll(secondHatchPath.generate(secondSequence,
			// r.isSwapped()));
			// if(secondSequence != null){
			// preloadedAutoCommands.add(new
			// Titan.ConditionalCommand<>((rob)->!isRunningSequence()));
			// preloadedAutoCommands.addAll(getAutoAim(null, Sequence.OUTTAKE, r.isSwapped()
			// ? TargetType.FRONT_LEFT : TargetType.FRONT_RIGHT));
			// }
			// }
			Logger.l("Finished preloading");
			mimicLoaded = true;
		}).start();
	}
}
