package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.AutoConstants;
import frc.robot.Systems;
import frc.robot.commands.DriveTime;

/**
 * Note: in a separate class to easily add commands if needed
 * @author Colin Wong
 */
public class DriveForward extends SequentialCommandGroup {
    
    public DriveForward(Systems systems) {
        addCommands(
            new DriveTime(systems, AutoConstants.FORWARD_AUTO.getFirst(), AutoConstants.FORWARD_AUTO.getSecond())
        );
    }
}
