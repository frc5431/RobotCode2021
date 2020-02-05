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

	private CommandQueue<Robot> sequenceCommands, drivebaseCommands, preloadedAutoCommands;
	private HashMap<Integer, Sequence> sequences = new HashMap<>();

	private Sequence runningSequence = null;

	public Auton() {
		sequenceCommands = new CommandQueue<>();
		drivebaseCommands = new CommandQueue<>();
		preloadedAutoCommands = new CommandQueue<>();


	}

	@Override
	public void init(final Robot robot) {
		switch (robot.getMode()) {
		case AUTO:
			robot.getDrivebase().resetSensors();
			drivebaseCommands.clear();
			drivebaseCommands.add(new WaitCommand<>(100));
			drivebaseCommands.addAll(preloadedAutoCommands);
			preloadedAutoCommands.clear();
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

	public CommandQueue<Robot> getPreloadedAutoCommands() {
		return preloadedAutoCommands;
	}

	public List<Command<Robot>> goToPosition(Robot robot, final List<Command<Robot>> preCommands) {
		return null;
	}
}
