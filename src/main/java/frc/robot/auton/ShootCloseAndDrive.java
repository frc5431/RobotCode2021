package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Systems;
import frc.robot.commands.DriveTime;
import frc.robot.commands.states.ShootSuperCommand;
import frc.robot.util.ShootPosition;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.vision.Limelight;

/**
 * @author Rishmita Rao
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class ShootCloseAndDrive extends SequentialCommandGroup {

	public ShootCloseAndDrive(Systems systems, Limelight limelight) {
		addCommands(
			new ShootSuperCommand(systems, ShootPosition.AUTON, true),
            new InstantCommand(()-> Logger.l("exiting shoot super command")),
			new WaitCommand(0.2),
			new DriveTime(systems, -0.25, 500),
			new WaitCommand(0.2),
			new DriveTime(systems, 0.25, 1250)
		);
	}
}