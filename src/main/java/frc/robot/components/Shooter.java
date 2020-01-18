package frc.robot.components;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Shooter extends Component<Robot> {

    WPI_TalonFX flywheel, feedLeft, feedRight;
    SpeedControllerGroup feed;

    Toggle feedToggle, flywheelToggle;

    public Shooter() {
        flywheel = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_ID);
        flywheel.setInverted(Constants.SHOOTER_FLYWHEEL_REVERSE);
        flywheel.setNeutralMode(NeutralMode.Coast);

        feedLeft = new WPI_TalonFX(Constants.SHOOTER_FEEDER_LEFT_ID);
        feedLeft.setInverted(Constants.SHOOTER_FEEDER_LEFT_REVERSE);
        feedLeft.setNeutralMode(NeutralMode.Brake);

        feedRight = new WPI_TalonFX(Constants.SHOOTER_FEEDER_RIGHT_ID);
        feedRight.setInverted(Constants.SHOOTER_FEEDER_RIGHT_REVERSE);
        feedRight.setNeutralMode(NeutralMode.Brake);

        feed = new SpeedControllerGroup(feedLeft, feedRight);

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
            flywheel.set(1);
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
}