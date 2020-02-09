package frc.robot.auto.commands;

import frc.robot.Robot;
import frc.robot.util.states.IntakeState;
import frc.team5431.titan.core.robot.Command;

/**
 * @author Ryan Hirasaki
 */
public class IntakeCommand extends Command<Robot> {

    private final IntakeState state;

    public IntakeCommand(IntakeState state) {
        this.state = state;

        name = "FlywheelCommand";
        properties = String.format("Velocity: %s", state.toString());
    }


    @Override
    public void init(Robot robot) {
    }

    @Override
    public CommandResult update(Robot robot) {
        return null;
    }

    @Override
    public void done(Robot robot) {
    }
}