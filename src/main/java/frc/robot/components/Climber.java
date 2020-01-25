package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.robot.Component;

public class Climber extends Component<Robot> {

    WPI_TalonFX motor;

    public Climber() {
        motor = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);
        motor.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
    }

    @Override
    public void disabled(Robot arg0) {
    }

    @Override
    public void init(Robot arg0) {
    }

    @Override
    public void periodic(Robot arg0) {
    }

    public void setSpeed(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public double getRotations() {
        return motor.getSensorCollection().getIntegratedSensorPosition() * 8192;
    }

}