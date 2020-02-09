package frc.robot.auto.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.components.Elevator;
import frc.robot.util.states.ClimberState;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.robot.Command;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class ElevateCommand extends Command<Robot> {
    private final ClimberState targetPosition;

    public ElevateCommand(final ClimberState position) {
        this.targetPosition = position;

        name = "ElevateCommand";
        properties = String.format("Position: %s", position.toString());
    }

    private boolean isComplete(final Elevator elevator) {
        final boolean approxPosition = Calc.approxEquals(elevator.getEncoderPosition(), targetPosition.getPosition(),
                Constants.ELEVATOR_POSITION_TOLARANCE);

        return (targetPosition.getPosition() > 0 && approxPosition);
    }

    private void runElevator(final Elevator elevator) {
        final double encoderPos = elevator.getEncoderPosition();
        if (targetPosition.getPosition() <= 0 && encoderPos <= 1000) {

        } else {

        }
    }

    @Override
    public void init(final Robot robot) {
        final Elevator elevator = robot.getElevator();

        if (isComplete(elevator)) {
            runElevator(elevator);
        }
    }

    @Override
    public CommandResult update(final Robot robot) {
        final Elevator elevator = robot.getElevator();

        if (elevator.getControlMode() == ComponentControlMode.MANUAL) {
            robot.getAuton().abort(robot);
            return CommandResult.CLEAR_QUEUE;
        }

        if (isComplete(elevator)) {
            elevator.setSpeed(0.0);
            elevator.setControlMode(ComponentControlMode.MANUAL);
            return CommandResult.COMPLETE;
        }

        runElevator(elevator);

        return CommandResult.IN_PROGRESS;
    }

    @Override
    public void done(final Robot robot) {
    }
}