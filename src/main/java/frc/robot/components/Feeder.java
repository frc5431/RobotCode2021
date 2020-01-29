package frc.robot.components;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Feeder extends Component<Robot> {

    WPI_TalonFX feed;

    Toggle feedToggle;
    double shooterSpeed = 0.50;
    double feedSpeed = 0.5;

    public Feeder() {
    
        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);


        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        feedToggle = new Toggle();

        feedToggle.setState(false);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if (feedToggle.getState()) {
            feed.set(feedSpeed);
        } else {
            feed.set(0);
        }
    }

    @Override
    public void disabled(Robot robot) {
    }

    public Toggle getFeedToggle() {
        return feedToggle;
    }

    public void setFeedSpeed(double feedSpeed) {
        this.feedSpeed = feedSpeed;
    }
}