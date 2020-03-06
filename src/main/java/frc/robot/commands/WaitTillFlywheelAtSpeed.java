package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;

public class WaitTillFlywheelAtSpeed extends CommandBase {

	private final Flywheel flywheel;
	private final boolean rpmWait;

	public WaitTillFlywheelAtSpeed(Flywheel flywheel, boolean rpmWait) {
		this.flywheel = flywheel;
		this.rpmWait = rpmWait;
	}

	@Override
	public boolean isFinished() {
		return !(!rpmWait && (!flywheel.atVelocity() || flywheel.getTargetVelocity() == 0));
	}
}