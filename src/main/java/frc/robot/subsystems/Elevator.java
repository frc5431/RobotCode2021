package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.states.ClimberState;

public class Elevator extends SubsystemBase {

    private WPI_TalonFX elevator;

    public Elevator() {
        elevator = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);
        elevator.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
        elevator.setNeutralMode(Constants.CLIMBER_ELEVATOR_NEUTRALMODE);

        // reset encoder
        elevator.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator Position", elevator.getSelectedSensorPosition());
    }

    public void setPosition(ClimberState position) {
        elevator.set(ControlMode.Position, position.getPosition());
    }

    public void setSpeed(double speed) {
        // Encoder Clicks
        int encoder = getEncoderPosition();

        // If Going down set a lower limit of a encoder value of the constant lower limit
        if (speed < 0 && encoder < Constants.CLIMBER_ELEVATOR_LOWER_LIMIT)
            speed = 0;
        // If Going up set a upper limit of a encoder value of the constant upper limit
        else if (speed > 0 && encoder > Constants.CLIMBER_ELEVATOR_UPPER_LIMIT)
            speed = 0;
        elevator.set(ControlMode.PercentOutput, speed);
    }

    public int getEncoderPosition() {
        return elevator.getSelectedSensorPosition();
    }

    public double getRotations() {
        return getEncoderPosition() / 2048;
    }
}