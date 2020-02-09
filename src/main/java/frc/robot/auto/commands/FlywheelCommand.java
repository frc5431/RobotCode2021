package frc.robot.auto.commands;

import frc.robot.Robot;
import frc.robot.util.states.FlywheelState;
import frc.team5431.titan.core.robot.Command;

/**
 * @author Ryan Hirasaki
 */
public class FlywheelCommand extends Command<Robot> {

    private final FlywheelState state;

    public FlywheelCommand(FlywheelState state) {
        this.state = state;

        name = "FlywheelCommand";
        properties = String.format("Velocity: %s", state.toString());
    }

    @Override
    public void init(Robot robot) {
    }

    // FIXME: Implement flywheel controlles
    @Override
    public CommandResult update(Robot robot) {
        return null;
    }

    @Override
    public void done(Robot robot) {
    }
}