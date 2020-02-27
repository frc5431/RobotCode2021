package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * @author Colin Wong
 */
public class WaitForButtonCommand extends CommandBase {
    private JoystickButton button;

    /**
     * @param button the button to monitor
     */
    public WaitForButtonCommand(JoystickButton button) {
        this.button = button;
    }

    @Override
    public boolean isFinished() {
        return button.get();
    }
}