package frc.robot.components;

import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.CommandQueue;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.WaitCommand;

public class Auton extends Component<Robot> {

	private CommandQueue<Robot> sequenceCommands, drivebaseCommands, preloadedAutoCommands;

	public Auton() {
		sequenceCommands = new CommandQueue<>();
		drivebaseCommands = new CommandQueue<>();
		preloadedAutoCommands = new CommandQueue<>();
	}

	@Override
	public void init(final Robot robot) {
		switch (robot.getMode()) {
		case AUTO:
			robot.getDrivebase(); // TODO: Reset Sensors
			drivebaseCommands.clear();
			drivebaseCommands.add(new WaitCommand<>(100));
			drivebaseCommands.addAll(preloadedAutoCommands);
			preloadedAutoCommands.clear();
			break;
		case TEST:
			robot.getDrivebase(); // TODO: Reset Sensors
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
}
