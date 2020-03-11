package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class Flywheel extends SubsystemBase {
	public static enum Speeds {
		OFF(0), HALF(0.5), FULL(0.58);

		private double speed;

		private Speeds(double speed) {
			this.speed = speed;
		}

		public double getSpeed() {
			return speed;
		}
	}

	public static enum Velocity {
		OFF(0, -1), HALF(Constants.SHOOTER_FLYWHEEL_VELOCITY_LOW, Constants.SLOT_0),
		FULL(Constants.SHOOTER_FLYWHEEL_VELOCITY_HIGH, Constants.SLOT_0);

		private double speed;
		private int PIDSlot;

		private Velocity(double speed, int PIDSlot) {
			this.speed = speed;
			this.PIDSlot = PIDSlot;

		}

		public int getSlot() {
			return PIDSlot;
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

		assert (flywheel.getInverted() != _flywheelFollow.getInverted());

		flywheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.SLOT_0,
				Constants.DRIVEBASE_TIMEOUT_MS);
		

		// Set Neutral Mode
		flywheel.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);
		_flywheelFollow.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);

		flywheel.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);
		// flywheelRight.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);

		// reset encoder
		flywheel.setSelectedSensorPosition(0);

		// flywheel.setSensorPhase(true);

		ErrorCode err = ErrorCode.OK;

		err = flywheel.config_kF(Constants.SLOT_0, Constants.SHOOTER_FLYWHEEL_GAINS.kF);
		assert (err == ErrorCode.OK);
		err = flywheel.config_kP(Constants.SLOT_0, Constants.SHOOTER_FLYWHEEL_GAINS.kP);
		assert (err == ErrorCode.OK);
		err = flywheel.config_kI(Constants.SLOT_0, Constants.SHOOTER_FLYWHEEL_GAINS.kI);
		assert (err == ErrorCode.OK);
		err = flywheel.config_kD(Constants.SLOT_0, Constants.SHOOTER_FLYWHEEL_GAINS.kD);
		assert (err == ErrorCode.OK);
	}

	@Override
	public void periodic() {
		// Some asserts to make sure the follow command is working as we do not want to
		// break the gears or the falcons
		assert (flywheel.get() == _flywheelFollow.get());
		assert (flywheel.getInverted() != _flywheelFollow.getInverted());

		// Get Flywheel Velocity for state tuning: Far
		SmartDashboard.putNumber("Flywheel Velocity Current", flywheel.getSelectedSensorVelocity());

		// Get Flywheel PID error rate for analysis: Far
		SmartDashboard.putNumber("Flywheel Error Rate Current", flywheel.getClosedLoopError());

		SmartDashboard.putBoolean("Flywheel At Velocity", atVelocity());
	}

	public List<WPI_TalonFX> getMotors() {
		return List.of(new WPI_TalonFX[]{
			flywheel,
			_flywheelFollow
		});
	}

	private void setSlot(int slot) {
		// currentSlot = slot;
		// 
		// flywheel.selectProfileSlot(slot, Constants.REMOTE_0);
	}

	public void setSpeed(ControlMode mode, double speed) {
		assert (mode != null);
		flywheel.set(mode, speed);
	}

	public void set(Speeds speed) {
		setSpeed(ControlMode.PercentOutput, speed.getSpeed());
	}

	public void set(Velocity velocity) {
		setSlot(velocity.getSlot());
		if (Velocity.OFF == velocity)
			setSpeed(ControlMode.PercentOutput, 0);
		else
			setSpeed(ControlMode.Velocity, velocity.getSpeed());
	}

	public double getSpeed() {
		return flywheel.get();
	}

	public double getError(Velocity velocity) {
		return flywheel.get() == 0 ? 0 : flywheel.getClosedLoopError(velocity.getSlot());
	}

	public boolean atVelocity() {
		double targetVel = flywheel.getClosedLoopTarget();
		double currentVel = flywheel.getSelectedSensorVelocity();

		return Calc.approxEquals(Math.abs(targetVel), Math.abs(currentVel),Constants.FLYWHEEL_VELOCITY_RANGE);
	}

	public double getTargetVelocity() {
		double curr = flywheel.getSelectedSensorVelocity();
		if (DriverStation.getInstance().isEnabled())
			Logger.l("Retriving Flywheel Target: %f", curr);
		return curr;
	}

	public int getCurrentVelocity() {
		return flywheel.getSelectedSensorVelocity();
	}
}