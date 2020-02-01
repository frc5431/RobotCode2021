package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Flywheel extends Component<Robot> {

    WPI_TalonFX flywheelLeft, flywheelRight;

    Toggle flywheelToggle;
    double shooterSpeed;

    public Flywheel() {
        flywheelLeft = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_LEFT_ID);
        flywheelRight = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_RIGHT_ID);

        // Set Inverted Mode
        flywheelLeft.setInverted(Constants.SHOOTER_FLYWHEEL_REVERSE);
        flywheelRight.setInverted(!Constants.SHOOTER_FLYWHEEL_REVERSE); // Inverted via "!"

        flywheelRight.follow(flywheelLeft);

        // Set Neutral Mode
        flywheelLeft.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);
        // flywheelRight.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);

        flywheelLeft.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);
        // flywheelRight.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);

        // Toggle Control
        flywheelToggle = new Toggle();
        flywheelToggle.setState(false);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if (flywheelToggle.getState()) {
            flywheelLeft.set(shooterSpeed);
            flywheelRight.set(shooterSpeed);
        } else {
            flywheelLeft.set(0);
            flywheelRight.set(0);
        }
    }

    @Override
    public void disabled(Robot robot) {
    }

    public Toggle getFlywheelToggle() {
        return flywheelToggle;
    }

    public double getFlywheelVelocity() {
        return (flywheelRight.getSensorCollection().getIntegratedSensorVelocity());
    }

    public double getFlywheelSpeed() {
        return flywheelRight.get();
    }

    public void setShooterSpeed(double shooterSpeed) {
        this.shooterSpeed = shooterSpeed;
    }
}