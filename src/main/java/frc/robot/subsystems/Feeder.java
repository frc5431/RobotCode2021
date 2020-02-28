package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    HashMap<Integer, DigitalInput> dioSensors = new HashMap<Integer, DigitalInput>();

    public Feeder() {
        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);

        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        dioSensors.forEach((num, sensor) -> {
            sensor = new DigitalInput(Constants.DIGITAL_INPUT_IDS[num]);
        });
    }

    @Override
    public void periodic() {
        dioSensors.forEach((num, sensor) -> {
            SmartDashboard.putBoolean("DIO Sensor " + num, sensor.get());
        });
    }

    public void set(double speed) {
        feed.set(ControlMode.PercentOutput, speed);
    }

    public HashMap<Integer, DigitalInput> getDIOSensors() {
        return dioSensors;
    }

    public boolean getValueOfDIOSensor(int num) {
        return dioSensors.get(num).get();
    }
}