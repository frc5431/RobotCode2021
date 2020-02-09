package frc.robot.components;

import java.util.HashMap;
import java.util.List;

import frc.robot.Robot;
import frc.robot.auto.Sequence;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.robot.CommandQueue;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.WaitCommand;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class Auton extends Component<Robot> {

	private CommandQueue<Robot> sequenceCommands, drivebaseCommands;

	// Predefined Sequences via button presses on operator controller buttons
	private HashMap<Integer, Sequence> operatorSequences = new HashMap<>();

	private Sequence runningSequence = null;

	public Auton() {
		sequenceCommands = new CommandQueue<>();
		drivebaseCommands = new CommandQueue<>();

		// Climber Buttons
		// operatorSequences.put(Xbox.Button.X, Sequence.STOW);
		// operatorSequences.put(Xbox.Button.Y, Sequence.CLIMB);
	}

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

	public List<Command<Robot>> goToPosition(Robot robot, final List<Command<Robot>> preCommands) {
		return null;
	}
}
