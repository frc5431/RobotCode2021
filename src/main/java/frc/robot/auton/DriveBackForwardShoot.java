package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.misc.Logger;
import frc.robot.Systems;
import frc.robot.commands.*;
import frc.robot.commands.states.*;

/**
 * @author Colin Wong
 */
public class DriveBackForwardShoot extends SequentialCommandGroup {
    public DriveBackForwardShoot(Systems systems) {
        addCommands(
            new ShootSuperCommand(systems, true, true),
            new InstantCommand(()-> Logger.l("exiting shoot super command")),
            new WaitCommand(0.2),
            new DriveTime(systems, -0.3, 500),
            new WaitCommand(0.2),
            new DriveTime(systems, 0.3, 1250)
        );
    }
}