package frc.robot.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Albert Ma
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class Feeder extends SubsystemBase {
    public enum FeederStateTeleop {
        LOAD, COMPRESS, AUTO_REVERSE, READY
    }

    WPI_TalonFX feed;
    HashMap<Integer, DigitalInput> dioSensors = new HashMap<Integer, DigitalInput>(Constants.DIGITAL_INPUT_IDS.length);

    double feedSpeed, feedSpeedOffset = 0;
    int stopCount;
    boolean ballSeen, shootSeen;

    /**
     * Ball stop time = time for when the feeder should stop moving after it sees a
     * ball Up stop time = time for when the feeder should stop moving three balls
     * up Final stop time = time for when the feeder should stop reversing 
     */
    long finalStopTime, upStopTime, ballStopTime;

    FeederStateTeleop _state = FeederStateTeleop.LOAD;

    int ballCount = 0;
	boolean shooting = false;
	private final PowerDistributionPanel pdp;

    public Feeder(PowerDistributionPanel pdp) {
		this.pdp = pdp;

        feed = new WPI_TalonFX(Constants.SHOOTER_FEEDER_ID);

        feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
        feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

        dioSensors.put(0,new DigitalInput(Constants.DIGITAL_INPUT_IDS[0]));
        dioSensors.put(1,new DigitalInput(Constants.DIGITAL_INPUT_IDS[1])); 
        dioSensors.put(2,new DigitalInput(Constants.DIGITAL_INPUT_IDS[2]));
        dioSensors.put(3,new DigitalInput(Constants.DIGITAL_INPUT_IDS[3])); 

        // dioSensors.forEach((num, sensor) -> {
        //     sensor = new DigitalInput(Constants.DIGITAL_INPUT_IDS[num]);
        // });

        resetVars();
    }

    @Override
    public void periodic() {
        dioSensors.forEach((num, sensor) -> {
            SmartDashboard.putBoolean("DIO Sensor " + num, sensor.get());
        });

        ballUpdate();

		feederLoadAndShoot();
		
		if(15 <= pdp.getCurrent(Constants.PIVOT_PDP_SLOT)) {
			// Slow down indexer
			feedSpeedOffset = 0.2;
		}
		else {
			feedSpeedOffset = 0.0;
		}

        SmartDashboard.putNumber("Ball Count", ballCount);
        SmartDashboard.putString("Feeder State", _state.name());
        SmartDashboard.putNumber("Feed Speed", feedSpeed);
    }

    public void set(double speed) {
        feedSpeed = speed;
    }

    public HashMap<Integer, DigitalInput> getDIOSensors() {
        return dioSensors;
    }

    public boolean getValueOfDIOSensor(int num) {
        return dioSensors.get(num).get();
    }

    public void resetVars() {
        ballCount = 0;
        shooting = false;

        _state = FeederStateTeleop.LOAD;

        ballSeen = true;
        shootSeen = false;
    }

    /**
     * @author Colin Wong
     *         God
     */
    public void ballUpdate() {
        // ballCount incrementer - only increment if you see the ball after not seeing it.
        if (!ballSeen && !getValueOfDIOSensor(0) && _state != FeederStateTeleop.AUTO_REVERSE) {
            ballCount++;
            ballSeen = true;
            ballStopTime = System.currentTimeMillis() + Constants.SHOOTER_FEEDER_BALL_DELAY;
        }

        if (ballSeen && getValueOfDIOSensor(0) && _state != FeederStateTeleop.AUTO_REVERSE) {
            ballSeen = false;
        }

        // ballCount decrementer - only decrement if you see the ball after not seeing it.
        if (!shootSeen && shooting && !getValueOfDIOSensor(3)) {
            ballCount--;
            shootSeen = true;
            // Quick ballCount fixer - ballCount should never be negative.
            if (ballCount < 0) ballCount = 0;
        }

        if (shootSeen && shooting && getValueOfDIOSensor(3)) {
            shootSeen = false;
        }
    }

    public int getBallCount() {
        return ballCount;
    }

    public FeederStateTeleop getState() {
        return _state;
    }

    public void feederLoadAndShoot() {
        switch (_state) {
            case LOAD:
                // Run the feeder for a certain amount of time after it detects a ball entering.
                if (System.currentTimeMillis() < ballStopTime) { 
                    if (ballCount < 3) {
                        feed.set(Constants.SHOOTER_FEEDER_DEFAULT_SPEED - feedSpeedOffset);
                    } else {
                        // Runs if there are three balls.
                        feed.set(feedSpeed - feedSpeedOffset);
                    }
                } else {
                    // Waits for another ball to load.
                    feed.set(feedSpeed - feedSpeedOffset);
                }
                // After it loads three balls, it will continue to the next stage.
                if (ballCount >= 3) {
                    _state = FeederStateTeleop.COMPRESS;
                    upStopTime = System.currentTimeMillis() + Constants.SHOOTER_FEEDER_UP_DELAY;
                }
                break;
            case COMPRESS:
                // Move on after UP_DELAY ms.
                if (System.currentTimeMillis() < upStopTime) {
                    // Move the feeder up.
                    feed.set(Constants.SHOOTER_FEEDER_DEFAULT_SPEED - feedSpeedOffset);
                } else {
                    _state = FeederStateTeleop.AUTO_REVERSE;
                    finalStopTime = System.currentTimeMillis() + Constants.SHOOTER_FEEDER_DOWN_DELAY;
                }
                break;
            case AUTO_REVERSE:
                // Move on after DOWN_DELAY ms OR the balls clear the shoot sensor.
                if (System.currentTimeMillis() < finalStopTime && !getValueOfDIOSensor(3)) {
                    // Reverse the feeder.
                    feed.set(-Constants.SHOOTER_FEEDER_DEFAULT_SPEED - feedSpeedOffset);
                } else {
                    _state = FeederStateTeleop.READY;
                }
                break;
            case READY:
                // Ready to shoot.
                // Will not load until all balls have been emptied.
                shooting = true;

                if (ballCount > 0) {
                    feed.set(feedSpeed - feedSpeedOffset);
                } else {
                    _state = FeederStateTeleop.LOAD;
                    shooting = false;
                }
                break;
            default:
                feed.set(feedSpeed - feedSpeedOffset);
                break;
        }
    }
}