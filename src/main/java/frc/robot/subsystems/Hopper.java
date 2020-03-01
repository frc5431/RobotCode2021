package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** 
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class Hopper extends SubsystemBase {
    WPI_TalonSRX hopperLeft, hopperRight;

    public Hopper() {
        hopperLeft = new WPI_TalonSRX(Constants.HOPPER_LEFT_ID);
        hopperRight = new WPI_TalonSRX(Constants.HOPPER_RIGHT_ID);

        hopperLeft.setInverted(Constants.HOPPER_REVERSE);
        hopperRight.setInverted(!Constants.HOPPER_REVERSE);

        hopperLeft.setNeutralMode(Constants.HOPPER_NEUTRALMODE);
    }

    @Override
    public void periodic() {
        // Double Check if follow is working
        // assert (hopperLeft.get() == hopperRight.get());
    }

    public void set(double hopperSpeedLeft, double hopperSpeedRight) {
        // Hopper Right is following hopper left
        hopperLeft.set(ControlMode.PercentOutput, hopperSpeedLeft);
        hopperRight.set(ControlMode.PercentOutput, hopperSpeedRight);
    }
}