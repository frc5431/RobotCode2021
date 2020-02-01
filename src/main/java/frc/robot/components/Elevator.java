package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ClimberState;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Component;

public class Elevator extends Component<Robot> {

    private WPI_TalonFX elevator;
    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Elevator() {
        elevator = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);
        elevator.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
    }

    @Override
    public void init(final Robot robot) {
    }

    @Override
    public void periodic(final Robot robot) {
    }

    @Override
    public void disabled(final Robot robot) {
    }
    
    public void setPosition(ClimberState position) {
        elevator.set(ControlMode.Position, position.getPosition());
    }

    public void setSpeed(double speed) {
        elevator.set(ControlMode.PercentOutput, speed);
    }

    public double getEncoderPosition() {
        return elevator.getSensorCollection().getIntegratedSensorPosition();
    }

    public double getRotations() {
        return getEncoderPosition() / 2048;
    }

    /**
     * @param controlMode the controlMode to set
     */
    public void setControlMode(ComponentControlMode controlMode) {
        this.controlMode = controlMode;
    }

    /**
     * @return the controlMode
     */
    public ComponentControlMode getControlMode() {
        return controlMode;
    }
}