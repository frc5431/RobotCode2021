package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.states.ClimberState;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Component;

public class Elevator extends SubsystemBase {

    private WPI_TalonFX elevator;
    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Elevator() {
        elevator = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);
        elevator.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
        elevator.setNeutralMode(NeutralMode.Brake);
    }

    public void setPosition(ClimberState position) {
        elevator.set(ControlMode.Position, position.getPosition());
    }

    public void setSpeed(double speed) {
        elevator.set(ControlMode.PercentOutput, speed);
    }

    public double getEncoderPosition() {
        return elevator.getSelectedSensorPosition();
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