package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class Feeder extends SubsystemBase {
    WPI_TalonFX feed;
    DigitalInput zero, one, two, three;

    public Feeder() {
        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);

        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        zero  = new DigitalInput(Constants.DIGITAL_INPUT_START);
        one   = new DigitalInput(Constants.DIGITAL_INPUT_ONE);
        two   = new DigitalInput(Constants.DIGITAL_INPUT_TWO);
        three = new DigitalInput(Constants.DIGITAL_INPUT_THREE);
        
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("DIO 0", zero.get());
        SmartDashboard.putBoolean("DIO 1", one.get());
        SmartDashboard.putBoolean("DIO 2", two.get());
        SmartDashboard.putBoolean("DIO 3", three.get());
    }

    public void set(double speed) {
        feed.set(ControlMode.PercentOutput, speed);
    }
}