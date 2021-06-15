package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.AutoConstants;
import frc.robot.Systems;
import frc.robot.commands.DriveTime;

/**
 * @author Colin Wong
 */
public class DriveForwardBackward extends SequentialCommandGroup {
    
    public DriveForwardBackward(Systems systems) {
        addCommands(
            new DriveTime(systems, AutoConstants.FORWARD_AUTO.getFirst(), AutoConstants.FORWARD_AUTO.getSecond()),
            new WaitCommand(AutoConstants.WAIT_TIMEOUT),
            new DriveTime(systems, AutoConstants.BACKWARD_AUTO.getFirst(), AutoConstants.BACKWARD_AUTO.getSecond())
        );
    }
}
