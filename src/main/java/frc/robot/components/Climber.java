package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Robot.Mode;
import frc.robot.util.ClimberState;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Component;

public class Climber extends Component<Robot> {

    private WPI_TalonFX elevator, balancer;

    private ComponentControlMode elevatorMode = ComponentControlMode.MANUAL;

    public Climber() {
        elevator = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);
        elevator.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
        

        balancer = new WPI_TalonFX(Constants.CLIMBER_BALANCER_ID);
        balancer.setInverted(Constants.CLIMBER_BALANCER_REVERSE);
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

    public void setElevatorPosition(ClimberState position) {
        elevator.set(ControlMode.Position, position.getPosition());
    }

    public void setElevatorSpeed(double speed) {
        elevator.set(ControlMode.PercentOutput, speed);
    }

    public void setBalancerSpeed(double speed) {
        balancer.set(ControlMode.PercentOutput, speed);
    }

    public double getRotations() {
        return elevator.getSensorCollection().getIntegratedSensorPosition() / 2048;
    }

    public WPI_TalonFX getElevator() {
        return elevator;
    }

    public ComponentControlMode getElevatorMode() {
        return elevatorMode;
    }

    public void setElevatorMode(ComponentControlMode mode) {
        this.elevatorMode = mode;
    }

}