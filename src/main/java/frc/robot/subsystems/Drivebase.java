package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MotionMagic;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.misc.Logger;
/*
 * a lot of asserts were added as there are many things that can go wrong in this code
*/

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class Drivebase extends SubsystemBase {

    private PigeonIMU pidgey;

    private WPI_TalonFX left;
    private WPI_TalonFX right;

    private WPI_TalonFX _leftFollow;
    private WPI_TalonFX _rightFollow;

    private ErrorCode eCode = ErrorCode.OK;

    private double ramping;

    public Drivebase(WPI_TalonFX frontLeft, WPI_TalonFX frontRight, WPI_TalonFX rearLeft, WPI_TalonFX rearRight) {

        pidgey = new PigeonIMU(Constants.DRIVEBASE_PIGEON_IMU_ID);

        left = frontLeft;
        right = frontRight;

        _leftFollow = rearLeft;
        _rightFollow = rearRight;

        left.setInverted(Constants.DRIVEBASE_LEFT_REVERSE);
        right.setInverted(Constants.DRIVEBASE_RIGHT_REVERSE);
        _leftFollow.setInverted(Constants.DRIVEBASE_LEFT_REVERSE);
        _rightFollow.setInverted(Constants.DRIVEBASE_RIGHT_REVERSE);

        _leftFollow.follow(left);
        _rightFollow.follow(right);


        /* Factory Default all hardware to prevent unexpected behavior */
        eCode = left.configFactoryDefault();
        assert (eCode == ErrorCode.OK);
        eCode = right.configFactoryDefault();
        assert (eCode == ErrorCode.OK);
        eCode = pidgey.configFactoryDefault();
        assert (eCode == ErrorCode.OK);

        /* Set what state the motors will be at when the speed is at zero */
        left.setNeutralMode(Constants.DRIVEBASE_NEUTRAL_MODE);
        right.setNeutralMode(Constants.DRIVEBASE_NEUTRAL_MODE);

        /* Set the motor output ranges */
        eCode = left.configPeakOutputForward(1, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
        eCode = left.configPeakOutputReverse(-1, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
        eCode = right.configPeakOutputForward(1, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
        eCode = right.configPeakOutputReverse(-1, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        /* Tell motors which sensors it is reading from */
        eCode = left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        /* Tell the motors which sesnor specifically is being used */
        eCode = right.configRemoteFeedbackFilter(left.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor,
                Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
        eCode = right.configRemoteFeedbackFilter(pidgey.getDeviceID(), RemoteSensorSource.Pigeon_Yaw,
                Constants.DRIVEBASE_MOTIONMAGIC_TURN_REMOTE, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = right.configSelectedFeedbackSensor(FeedbackDevice.SensorSum,
                Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
        eCode = right.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1,
                Constants.DRIVEBASE_MOTIONMAGIC_TURN_REMOTE, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = right.configSelectedFeedbackCoefficient(0.5, Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE,
                Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
        eCode = right.configSelectedFeedbackCoefficient(1, Constants.DRIVEBASE_MOTIONMAGIC_TURN_REMOTE,
                Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        /* Set PID values for each slot */
        setPID(Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_SLOT, Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_GAINS);
        setPID(Constants.DRIVEBASE_MOTIONMAGIC_TURN_SLOT, Constants.DRIVEBASE_MOTIONMAGIC_TURN_GAINS);

        right.selectProfileSlot(Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_SLOT,
                Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE);
        right.selectProfileSlot(Constants.DRIVEBASE_MOTIONMAGIC_TURN_SLOT, Constants.DRIVEBASE_MOTIONMAGIC_TURN_REMOTE);

        zeroGyro();
        zeroDistance();

        setRamping(Constants.DRIVEBASE_DEFAULT_RAMPING);
    }

    private void setPID(final int slot, final MotionMagic gain) {
        eCode = right.config_kP(slot, gain.kP);
        assert (eCode == ErrorCode.OK);

        eCode = right.config_kI(slot, gain.kI);
        assert (eCode == ErrorCode.OK);

        eCode = right.config_kD(slot, gain.kD);
        assert (eCode == ErrorCode.OK);

        eCode = right.config_kF(slot, gain.kF);
        assert (eCode == ErrorCode.OK);

        eCode = right.config_IntegralZone(slot, gain.kIntegralZone, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = right.configClosedLoopPeakOutput(slot, gain.kPeakOutput, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = right.configClosedLoopPeriod(slot, gain.kClosedLoopTime, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
    }

    private void zeroDistance() {
        eCode = left.getSensorCollection().setIntegratedSensorPosition(0, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = right.getSensorCollection().setIntegratedSensorPosition(0, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
    }

    private void zeroGyro() {
        zeroDistance();

        pidgey.setYaw(0);
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Drivebase Left", left.get());
        SmartDashboard.putNumber("Drivebase Right", right.get());
        SmartDashboard.putNumber("Drivebase Right Encoder", right.getSelectedSensorPosition());
        SmartDashboard.putNumber("Drivebase Left Encoder", left.getSelectedSensorPosition());
        setRamping(ramping);

        // Check if the the motors are working together
        assert (left.get() == _leftFollow.get());
        assert (right.get() == _rightFollow.get());
    }

    public void setSlot(int slot) {
        // Add asserts as the motorcontrollers only support 4 slots
        assert (slot >= 0);
        assert (slot <= 3);

        right.selectProfileSlot(slot, 0);
    }

    public void drivePercentageTank(double driveLeft, double driveRight) {
        left.set(ControlMode.PercentOutput, driveLeft);
        right.set(ControlMode.PercentOutput, driveRight);
    }

    public void drivePercentageArcade(double power, double turn) {
        /*
         * Arbitrary based turning. Theoretically better as it is controlled by the
         * speed controller.
         */

        if (Math.abs(power) == 0) {
            power = 0;
        }

        left.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, -turn * Constants.DRIVEBASE_TURN_MAX_SPEED);
        right.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, +turn * Constants.DRIVEBASE_TURN_MAX_SPEED);

        // Logger.l("Power: %f, Turn: %f", power, turn);
    }

    public void driveMotionMagic(double distance, double angle) {
        left.follow(right, FollowerType.AuxOutput1);
        right.set(ControlMode.MotionMagic, distance, DemandType.AuxPID, angle);

        Logger.l("Distance: %f, Angle: %f", distance, angle);
    }

    public float getHeading() {
        return (float) pidgey.getCompassHeading();
    }

    public void setRamping(double ramping) {
        this.ramping = ramping;

        left.configOpenloopRamp(ramping);
        left.configClosedloopRamp(0);
        right.configOpenloopRamp(ramping);
        right.configClosedloopRamp(0);
    }

    public double getRamping() {
        return ramping;
    }

    public void resetSensors() {
        zeroDistance();
        zeroGyro();
    }

    public double getLeftSpeed() {
        return left.get();
    };

    public double getRightSpeed() {
        return right.get();
    };

    public double getLeftEncoderCount() {
        return left.getSelectedSensorPosition() / Constants.COUNTS_PER_REVOLUTION;
    };

    public double getRightEncoderCount() {
        return right.getSelectedSensorPosition() / Constants.COUNTS_PER_REVOLUTION;
    };

    public double getLeftDistance() {
        return getLeftEncoderCount() / Constants.COUNTS_PER_REVOLUTION * Constants.WHEEL_CIRCUMFERENCE
                * Constants.GEAR_RATIO;
    };

    public double getRightDistance() {
        return getRightEncoderCount() / Constants.COUNTS_PER_REVOLUTION * Constants.WHEEL_CIRCUMFERENCE
                * Constants.GEAR_RATIO;
    };

    public double getRPM() {
        return ((getLeftEncoderCount() * 600) + (getRightEncoderCount() * 600)) / 2;
    }

    public Pose2d getPose() {
        // return m_odometry.getPoseMeters();
        return null;
    }

    public boolean reachedMotionMagicTarget() {
        if (right.getControlMode() != ControlMode.MotionMagic) return true;
        return Calc.approxEquals(right.getActiveTrajectoryPosition(), right.getSelectedSensorPosition(), Constants.DRIVEBASE_MOTIONMAGIC_TOLERANCE);
    }

    // Trajectory and PathWeaver
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        left.setVoltage(leftVolts);
        right.setVoltage(-rightVolts);
        // m_drive.feed();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        // return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
        return null;
    }
    
    public List<WPI_TalonFX> getMotors() {
        return List.of(new WPI_TalonFX[]{
            left,
            right,
            _leftFollow,
            _rightFollow
        });
    }
}