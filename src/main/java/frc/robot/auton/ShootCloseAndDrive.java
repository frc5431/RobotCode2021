package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveTime;
import frc.robot.commands.states.ShootSuperCommand;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.vision.Limelight;

public class ShootCloseAndDrive extends SequentialCommandGroup {

	public ShootCloseAndDrive(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase, Limelight limelight) {
		addCommands(
			new ShootSuperCommand(intake, hopper, feeder, flywheel, drivebase, true, true),
			new DriveTime(drivebase, 0.3, 1000)
		);
	}
}