package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Speeds;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.robot.subsystems.LimelightSubsystem.Positions;
import frc.team5431.titan.core.vision.Limelight;
import frc.robot.commands.*;


/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 * @author Rishmita
 */
public class ShootSuperCommandFar extends SequentialCommandGroup {
    public ShootSuperCommandFar(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase, Limelight limelight) {
        addCommands(
            // Target
            new Targetor(drivebase, limelight, Positions.FULL),
             // Bring up to speed
            new FlywheelCommand(flywheel, Velocity.FULL,Velocity.FULL), // Waits till up to speed
            // Push Balls. Keep running until current command is interuppted
            new PushBallsUpSubCommand(intake, hopper, feeder) 
        );
    }
}