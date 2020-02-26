package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class Feeder extends SubsystemBase {
    WPI_TalonFX feed;

    public Feeder() {
    
        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);


        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);
    }

    @Override
    public void periodic() {
    }

    public void set(double speed) {
        feed.set(ControlMode.PercentOutput, speed);
    }
}