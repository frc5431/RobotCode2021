package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Shooter extends Component<Robot> {

    WPI_TalonFX flywheelLeft, flywheelRight, feed;
    SpeedControllerGroup flywheel;

    Toggle feedToggle, flywheelToggle;

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

        flywheel = new SpeedControllerGroup(flywheelLeft, flywheelRight);

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
        if (feedToggle.getState()) {
            feed.set(1);
        } else {
            feed.set(0);
        }

        if (flywheelToggle.getState()) {
            flywheel.set(robot.getDashboard().getNumber("Shooter Speed", 0.750));
        } else {
            flywheel.set(0);
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

    public double getFlywheelSpeed() {
        return flywheel.get();
    }
}