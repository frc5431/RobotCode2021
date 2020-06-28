package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Systems;
import frc.team5431.titan.core.vision.Limelight;

public class NineBall extends SequentialCommandGroup {
	private final Systems systems;
	private final Limelight limelight;

	public NineBall(Systems systems, Limelight limelight) {
		this.systems = systems;
		this.limelight = limelight;

		addCommands(
			new SixBall(systems, limelight)
		);
	}
}