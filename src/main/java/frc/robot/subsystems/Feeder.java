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

	public static boolean ENABLE_AUTO_FEEDER = true; // Should Be true by default

	WPI_TalonFX feed;
	HashMap<Integer, DigitalInput> dioSensors = new HashMap<Integer, DigitalInput>(Constants.DIGITAL_INPUT_IDS.length);

	double feedSpeed, feedSpeedOffset = 0;
	int stopCount;
	boolean ballSeen, shootSeen;
	boolean roof, topMid, lowMid, floor;

	/**
	 * Ball stop time = time for when the feeder should stop moving after it sees a
	 * ball Up stop time = time for when the feeder should stop moving three balls
	 * up Final stop time = time for when the feeder should stop reversing
	 */
	long finalStopTime, upStopTime, ballStopTime;

	FeederStateTeleop _state = FeederStateTeleop.LOAD;

	int ballCount = 0;
	boolean readyToShoot = false;
	boolean readyToLoad = false;
	boolean shooting = false;
	private final PowerDistributionPanel pdp;

	public Feeder(PowerDistributionPanel pdp, WPI_TalonFX feeder) {
		this.pdp = pdp;

		feed = feeder;

		feed.setInverted(Constants.SHOOTER_FEEDER_REVERSE);
		feed.setNeutralMode(Constants.SHOOTER_FEEDER_NEUTRALMODE);

		dioSensors.put(0, new DigitalInput(Constants.DIGITAL_INPUT_IDS[0]));
		dioSensors.put(1, new DigitalInput(Constants.DIGITAL_INPUT_IDS[1]));
		dioSensors.put(2, new DigitalInput(Constants.DIGITAL_INPUT_IDS[2]));
		dioSensors.put(3, new DigitalInput(Constants.DIGITAL_INPUT_IDS[3]));

		// dioSensors.forEach((num, sensor) -> {
		// sensor = new DigitalInput(Constants.DIGITAL_INPUT_IDS[num]);
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

		if (15 <= pdp.getCurrent(Constants.FEEDER_PDP_SLOT)) {
			// Slow down indexer
			feedSpeedOffset = 0.2;
		} else {
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
	 * @author Colin Wong God
	 */
	public void ballUpdate() {

		roof = getValueOfDIOSensor(3);
		topMid = getValueOfDIOSensor(2);
		lowMid = getValueOfDIOSensor(1);
		floor = getValueOfDIOSensor(0);

		boolean topThreeActive = !roof && !topMid && !lowMid;

		// if (roof)
		// ballCount++;
		// if (topMid)
		// ballCount++;
		// if (lowMid)
		// ballCount++;

		if (!floor && !topThreeActive) {
			readyToLoad = true;
		} else {
			readyToLoad = false;
		}

	}

	/**
	 * @return the ballCount
	 */
	public int getBallCount() {
		return ballCount;
	}

	public FeederStateTeleop getState() {
		return _state;
	}

	public void feederLoadAndShoot() {
		/*
		 * switch (_state) { case LOAD: // Run the feeder for a certain amount of time
		 * after it detects a ball entering. if (System.currentTimeMillis() <
		 * ballStopTime) { if (ballCount < 3) {
		 * feed.set(Constants.SHOOTER_FEEDER_DEFAULT_SPEED - feedSpeedOffset); } else {
		 * // Runs if there are three balls. feed.set(feedSpeed - feedSpeedOffset); } }
		 * else { // Waits for another ball to load. feed.set(feedSpeed -
		 * feedSpeedOffset); } // After it loads three balls, it will continue to the
		 * next stage. if (ballCount >= 3) { _state = FeederStateTeleop.COMPRESS;
		 * upStopTime = System.currentTimeMillis() + Constants.SHOOTER_FEEDER_UP_DELAY;
		 * } break; case COMPRESS: // Move on after UP_DELAY ms. if
		 * (System.currentTimeMillis() < upStopTime) { // Move the feeder up.
		 * feed.set(Constants.SHOOTER_FEEDER_DEFAULT_SPEED - feedSpeedOffset); } else {
		 * _state = FeederStateTeleop.AUTO_REVERSE; finalStopTime =
		 * System.currentTimeMillis() + Constants.SHOOTER_FEEDER_DOWN_DELAY; } break;
		 * case AUTO_REVERSE: // Move on after DOWN_DELAY ms OR the balls clear the
		 * shoot sensor. if (System.currentTimeMillis() < finalStopTime ||
		 * !getValueOfDIOSensor(3)) { // Reverse the feeder.
		 * feed.set(-Constants.SHOOTER_FEEDER_DEFAULT_SPEED - feedSpeedOffset); } else {
		 * _state = FeederStateTeleop.READY; } break; case READY: // Ready to shoot. //
		 * Will not load until all balls have been emptied. shooting = true;
		 * 
		 * if (ballCount > 0) { feed.set(feedSpeed - feedSpeedOffset); } else { _state =
		 * FeederStateTeleop.LOAD; shooting = false; } break; default:
		 * feed.set(feedSpeed - feedSpeedOffset); break; }
		 */

		if (feedSpeed == 0) {
			if(ENABLE_AUTO_FEEDER) {
				if (readyToLoad) {
					// Logger.l("Auto Feeder Running");
					feed.set(1 - (feedSpeedOffset * (feedSpeed >= 0 ? 1 : -1)));
				} else {
					// Logger.l("Auto Feeder Stopped");
					feed.set(0);
				}
			}
			else {
				feed.set(0);
			}
		} else {
			// Logger.l("Manual Feeder Ran at %f", feedSpeed);
			feed.set(feedSpeed);
		}

	}

	public boolean isEmpty() {
		return getValueOfDIOSensor(0) && getValueOfDIOSensor(1) && getValueOfDIOSensor(2) && getValueOfDIOSensor(3);
	}

	public boolean isFull() {
		return !(getValueOfDIOSensor(0) || getValueOfDIOSensor(1) || getValueOfDIOSensor(2) || getValueOfDIOSensor(3));
	}
}