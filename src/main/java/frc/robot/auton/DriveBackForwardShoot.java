package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.misc.Logger;
import frc.robot.commands.*;
import frc.robot.commands.states.*;

public class DriveBackForwardShoot extends SequentialCommandGroup {
    public DriveBackForwardShoot(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase) {
        addCommands(
            new ShootSuperCommand(intake, hopper, feeder, flywheel, drivebase, true, true),
            new InstantCommand(()-> Logger.l("exiting shoot super command")),
            new WaitCommand(0.2),
            new DriveTime(drivebase, -0.3, 500),
            new WaitCommand(0.2),
            new DriveTime(drivebase, 0.3, 1250)
        );
    }
}