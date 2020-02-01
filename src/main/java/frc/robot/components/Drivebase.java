package frc.robot.components;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.robot.util.MotionMagic;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

/*
 * a lot of asserts were added as there are many things that can go wrong in this code
*/

public class Drivebase extends Component<Robot> {

    private PigeonIMU pidgey;

    private WPI_TalonFX left;
    private WPI_TalonFX right;

    private WPI_TalonFX _leftFollow;
    private WPI_TalonFX _rightFollow;

    private Toggle swappedDrive;
    private ErrorCode eCode = ErrorCode.OK;

    private double ramping;
    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Drivebase() {

        pidgey = new PigeonIMU(Constants.DRIVEBASE_PIGEON_IMU_ID);

        left = new WPI_TalonFX(Constants.DRIVEBASE_FRONT_LEFT_ID);
        right = new WPI_TalonFX(Constants.DRIVEBASE_FRONT_RIGHT_ID);

        _leftFollow = new WPI_TalonFX(Constants.DRIVEBASE_BACK_LEFT_ID);
        _rightFollow = new WPI_TalonFX(Constants.DRIVEBASE_BACK_RIGHT_ID);

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

        swappedDrive = new Toggle();
        swappedDrive.setState(false);

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
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {

        setRamping(ramping);

        // Check if the the motors are working together
        assert (left.get() == _leftFollow.get());
        assert (right.get() == _rightFollow.get());
    }

    @Override
    public void disabled(Robot robot) {
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

        if(Math.abs(power) == 0) {
            power = 0;
        }

        left.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, -turn * 0.35);
        right.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, +turn * 0.35);

        Logger.l("Power: %f", power);
        Logger.l("Turn: %f", turn);
    }

    public void driveMotionMagic(double distance, double angle) {
        left.follow(right, FollowerType.AuxOutput1);
        right.set(ControlMode.MotionMagic, distance, DemandType.AuxPID, angle);

        Logger.l("Distance: %f", distance);
        Logger.l("Angle: %f", angle);
    }

    public double getHeading() {
        return pidgey.getCompassHeading();
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

    /**
     * @param controlMode the controlMode to set
     */
    public void setControlMode(ComponentControlMode controlMode) {
        this.controlMode = controlMode;
    }

    /**
     * @return the controlMode
     */
    public ComponentControlMode getControlMode() {
        return controlMode;
    }
}