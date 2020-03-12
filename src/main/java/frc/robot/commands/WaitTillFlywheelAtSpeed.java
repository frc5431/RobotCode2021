package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;

public class WaitTillFlywheelAtSpeed extends CommandBase {

	private final Systems systems;
	private final boolean rpmWait;

	public WaitTillFlywheelAtSpeed(Systems systems, boolean rpmWait) {
		this.systems = systems;
		this.rpmWait = rpmWait;
	}

	@Override
	public boolean isFinished() {
		return !(!rpmWait && (!systems.getFlywheel().atVelocity() || systems.getFlywheel().getTargetVelocity() == 0));
	}
}