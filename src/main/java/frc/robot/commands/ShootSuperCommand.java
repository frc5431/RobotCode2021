package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Speeds;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class ShootSuperCommand extends SequentialCommandGroup {
    public ShootSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel) {
        addCommands(
            // Bring up to speed
            new FlywheelControl(flywheel, Speeds.FULL),

            // Push Balls. Keep running until current command is interuppted
            new PushBallsUpSubCommand(intake, hopper, feeder) 
        );
    }
}