package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Feeder extends Component<Robot> {

    WPI_TalonFX feed;

    Toggle feedToggle, reverse;
    double shooterSpeed = 0.50;
    double feedSpeed;

    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Feeder() {
    
        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);


        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        feedToggle = new Toggle();
        feedToggle.setState(false);

        reverse = new Toggle();
        reverse.setState(false);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if (feedToggle.getState()) {
            if (reverse.getState())
                feed.set(-1 * feedSpeed);
            else
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

    /**
     * @return the controlMode
     */
    public ComponentControlMode getControlMode() {
        return controlMode;
    }

    /**
     * @param controlMode the controlMode to set
     */
    public void setControlMode(ComponentControlMode controlMode) {
        this.controlMode = controlMode;
    }

    /**
     * @return the reverse
     */
    public Toggle getReverse() {
        return reverse;
    }
}