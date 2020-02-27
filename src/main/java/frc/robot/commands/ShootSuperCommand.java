package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Speeds;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class ShootSuperCommand extends SequentialCommandGroup {
    public ShootSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, JoystickButton button) {
        addCommands(
            new FlywheelControl(flywheel, Speeds.FULL),
            new WaitForButtonCommand(button),
            new PushBallsUpSubCommand(intake, hopper, feeder)
        );
    }
}