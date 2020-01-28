package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Shooter extends Component<Robot> {

    WPI_TalonFX flywheelLeft, flywheelRight, feed;

    Toggle feedToggle, flywheelToggle;
    double shooterSpeed = 0.50;
    double feedSpeed = 0.5;

    public Shooter() {
        flywheelLeft = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_LEFT_ID);
        flywheelLeft.setInverted(Constants.SHOOTER_FLYWHEEL_LEFT_REVERSE);
        flywheelLeft.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);

        flywheelRight = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_RIGHT_ID);
        flywheelRight.setInverted(Constants.SHOOTER_FLYWHEEL_RIGHT_REVERSE);
        flywheelRight.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);

        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);
        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        feedToggle = new Toggle();
        flywheelToggle = new Toggle();

        feedToggle.setState(false);
        flywheelToggle.setState(false);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        flywheelLeft.follow(flywheelRight);
        if (feedToggle.getState()) {
            feed.set(feedSpeed);
        } else {
            feed.set(0);
        }

        if (flywheelToggle.getState()) {
            // TODO: Something is wrong with the flywheel firmware so disable
            // flywheelLeft.set(shooterSpeed);
            // flywheelRight.set(shooterSpeed);
        } else {
            flywheelLeft.set(0);
            flywheelRight.set(0);
        }
    }

    @Override
    public void disabled(Robot robot) {
    }

    public Toggle getFeedToggle() {
        return feedToggle;
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

    public void setFeedSpeed(double feedSpeed) {
        this.feedSpeed = feedSpeed;
    }
}