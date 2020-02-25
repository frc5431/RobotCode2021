package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.ComponentControlMode;

public class Feeder extends SubsystemBase {

    WPI_TalonFX feed;

    // Toggle feedToggle, reverse;
    double shooterSpeed = 0.50;
    double feedSpeed;

    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Feeder() {
    
        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);


        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        // feedToggle = new Toggle();
        // feedToggle.setState(false);

        // reverse = new Toggle();
        // reverse.setState(false);
    }

    @Override
    public void periodic() {
        feed.set(feedSpeed);
    }

    // public Toggle getFeedToggle() {
    //     return feedToggle;
    // }

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

    // /**
    //  * @return the reverse
    //  */
    // public Toggle getReverse() {
    //     return reverse;
    // }
}