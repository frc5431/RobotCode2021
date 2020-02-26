package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Flywheel extends SubsystemBase {
    public static enum Speeds {
        OFF(0), HALF(.25), FULL(0.5);

        private double speed;
        private Speeds(double speed) {
            this.speed = speed;
        } 

        public double getSpeed() {
            return speed;
        }
    }

    WPI_TalonFX flywheel, _flywheelFollow;

    public Flywheel() {
        flywheel = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_LEFT_ID);
        _flywheelFollow = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_RIGHT_ID);

        _flywheelFollow.follow(flywheel);

        // Set Inverted Mode
        flywheel.setInverted(Constants.SHOOTER_FLYWHEEL_REVERSE);
        _flywheelFollow.setInverted(InvertType.OpposeMaster); // Inverted via "!"

        assert (flywheel.getInverted() == !_flywheelFollow.getInverted());

        // Set Neutral Mode
        flywheel.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);
        _flywheelFollow.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);

        flywheel.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);
        // flywheelRight.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);
    }

    @Override
    public void periodic() {
        assert (flywheel.get() == _flywheelFollow.get());
    }

    public void setSpeed(ControlMode mode, double speed) {
        assert (mode != null);
        flywheel.set(mode, speed);
    }

    public void set(Speeds speed) {
        setSpeed(ControlMode.PercentOutput, speed.getSpeed());
    }

    public double getSpeed() {
        return flywheel.getMotorOutputPercent();
    }
}