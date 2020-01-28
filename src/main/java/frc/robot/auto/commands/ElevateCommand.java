package frc.robot.auto.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.components.Climber;
import frc.robot.util.ClimberState;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.robot.Command;

public class ElevateCommand extends Command<Robot> {
    private final int targetPosition;

    public ElevateCommand(final int position) {
        this.targetPosition = position;

        name = "ElevateToCommand";
        properties = String.format("Position: %d", position);
    }

    private boolean isComplete(final Climber climber) {
        final boolean approxPosition = Calc.approxEquals(
                climber.getElevator().getSensorCollection().getIntegratedSensorPosition(), targetPosition,
                Constants.ELEVATOR_POSITION_TOLARANCE);

        return (targetPosition > 0 && approxPosition) || (targetPosition <= 0 && false); // TODO: check 2019 code
    }

    private void runElevator(final WPI_TalonFX elevator){
        final double encoderPos = elevator.getSensorCollection().getIntegratedSensorPosition();
        if(targetPosition <= 0 && encoderPos <= 1000) {

        }
        else {
            
        }
    }

    @Override
    public void init(final Robot robot) {
        final Climber climber = robot.getClimber();

        if (isComplete(climber)) {
            runElevator(climber.getElevator());
        }
    }

    @Override
    public CommandResult update(final Robot robot) {
        final Climber climber = robot.getClimber();

		if(climber.getElevatorMode() == ComponentControlMode.MANUAL){
        }

        if(isComplete(climber)) {
            climber.setElevatorSpeed(0.0);
            climber.setElevatorMode(ComponentControlMode.MANUAL);
            return CommandResult.COMPLETE;
        }
        return null;
    }

    @Override
    public void done(final Robot robot) {
    }
}