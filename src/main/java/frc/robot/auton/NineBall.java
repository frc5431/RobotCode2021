package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class NineBall extends SequentialCommandGroup {
	private final Drivebase drivebase;

	public NineBall(Drivebase drivebase) {
		this.drivebase = drivebase;
	}
}