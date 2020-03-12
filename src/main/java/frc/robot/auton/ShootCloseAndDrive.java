package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Systems;
import frc.robot.commands.DriveTime;
import frc.robot.commands.states.ShootSuperCommand;
import frc.team5431.titan.core.vision.Limelight;

public class ShootCloseAndDrive extends SequentialCommandGroup {

	public ShootCloseAndDrive(Systems systems, Limelight limelight) {
		addCommands(
			new ShootSuperCommand(systems, true, true),
			new DriveTime(systems, 0.25, 2)
		);
	}
}