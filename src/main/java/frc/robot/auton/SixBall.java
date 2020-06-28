package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivebase;

public class SixBall extends SequentialCommandGroup {

	private final Drivebase drivebase;

	public SixBall(Drivebase drivebase) {
		this.drivebase = drivebase;
	}

}