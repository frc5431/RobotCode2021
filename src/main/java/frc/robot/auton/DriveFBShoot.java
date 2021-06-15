package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.AutoConstants;
import frc.robot.Systems;
import frc.robot.commands.DriveTime;
import frc.robot.commands.states.ShootSuperCommand;
import frc.robot.util.ShootPosition;
import frc.team5431.titan.core.vision.Limelight;

/**
 * @author Rishmita Rao
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class DriveFBShoot extends SequentialCommandGroup {

	public DriveFBShoot(Systems systems, Limelight limelight) {
		addCommands(
            new DriveTime(systems, AutoConstants.FORWARD_AUTO.getFirst(), AutoConstants.FORWARD_AUTO.getSecond()),
            new WaitCommand(AutoConstants.WAIT_TIMEOUT),
            new DriveTime(systems, AutoConstants.BACKWARD_AUTO.getFirst(), AutoConstants.BACKWARD_AUTO.getSecond()),
            new WaitCommand(AutoConstants.WAIT_TIMEOUT),
			new ShootSuperCommand(systems, ShootPosition.AUTON, false)
		);
	}
}