package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PauseSubsystem extends CommandBase {
	public PauseSubsystem(SubsystemBase base) {
		addRequirements(base);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}