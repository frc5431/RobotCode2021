package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 * @author Rishmita Rao
 * @author Daniel Brubaker
 */
public class Elevator extends SubsystemBase {

    private WPI_TalonFX elevator;

    public Elevator(WPI_TalonFX elevatorFalcon) {
        elevator = elevatorFalcon;
        elevator.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
        elevator.setNeutralMode(Constants.CLIMBER_ELEVATOR_NEUTRALMODE);

        // reset encoder
        this.resetEncoder();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator Position", elevator.getSelectedSensorPosition());
    }

   

    public void setSpeed(double speed) {
        // Encoder Clicks
        double encoder = getEncoderPosition();
        boolean limitReached = false;

        // If Going down set a lower limit of a encoder value of the constant lower limit
        if (speed <= 0 && encoder < Constants.CLIMBER_ELEVATOR_LOWER_LIMIT) {
            speed = 0;
            limitReached = true;
        }

        // If Going up set a upper limit of a encoder value of the constant upper limit
        else if (speed >= 0 && encoder > Constants.CLIMBER_ELEVATOR_UPPER_LIMIT) {
            speed = 0;
            limitReached = true;
        }

        SmartDashboard.putBoolean("Elevator Limit Reached", limitReached);
        elevator.set(ControlMode.PercentOutput, speed);
    }

    public double getEncoderPosition() {
        return elevator.getSelectedSensorPosition();
    }

    public double getRotations() {
        return getEncoderPosition() / 2048;
    }

    public void resetEncoder() {
        elevator.setSelectedSensorPosition(0);
    }

    public List<WPI_TalonFX> getMotors() {
        return List.of(elevator);
    }
}