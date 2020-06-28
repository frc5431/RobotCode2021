package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Systems;
import frc.robot.commands.DriveTime;
import frc.robot.commands.Targetor;
import frc.robot.commands.states.FloorIntakeCommand;
import frc.robot.commands.states.StowSuperCommand;
import frc.team5431.titan.core.vision.Limelight;

public class SixBall extends SequentialCommandGroup {

	private final Systems systems;
	private final Limelight limelight;

	public SixBall(Systems systems, Limelight limelight) {
		this.systems = systems;
		this.limelight = limelight;

		// FIXME: Timings need to be fixed
		addCommands(
			new DriveBackForwardShoot(systems),
			new DriveArc(systems, -45, 0),
			new DriveTime(systems, 0.5, 1),
			new DriveArc(systems, 45, 0),
			new FloorIntakeCommand(systems),
			new DriveTime(systems, 0.75, 1),
			new StowSuperCommand(systems),
			new DriveTime(systems, -0.75, 1),
			new Targetor(systems, limelight)
		);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		systems.getDrivebase().drivePercentageTank(0, 0);
	}
}