package frc.robot.components;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.MotionMagic;
import frc.robot.util.MotionMagicCommands;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Drivebase extends Component<Robot> {

    private PigeonIMU pidgey;

    private WPI_TalonFX left;
    private WPI_TalonFX right;

    private WPI_TalonFX _leftFollow;
    private WPI_TalonFX _rightFollow;

    private Toggle swappedDrive;

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
        left.configFactoryDefault();
        right.configFactoryDefault();
        pidgey.configFactoryDefault();

        /* Set what state the motors will be at when the speed is at zero */
        left.setNeutralMode(Constants.DRIVEBASE_NEUTRAL_MODE);
        right.setNeutralMode(Constants.DRIVEBASE_NEUTRAL_MODE);

        /* Set the motor output ranges */
        left.configPeakOutputForward(1, Constants.DRIVEBASE_TIMEOUT_MS);
        left.configPeakOutputReverse(-1, Constants.DRIVEBASE_TIMEOUT_MS);
        right.configPeakOutputForward(1, Constants.DRIVEBASE_TIMEOUT_MS);
        right.configPeakOutputReverse(-1, Constants.DRIVEBASE_TIMEOUT_MS);

        /* Tell motors which sensors it is reading from */
        left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.DRIVEBASE_TIMEOUT_MS);
        changeRemoteSensor(MotionMagicCommands.FOWARD);
        right.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, 0, Constants.DRIVEBASE_TIMEOUT_MS);
        right.configSelectedFeedbackCoefficient(0.5, 0, Constants.DRIVEBASE_TIMEOUT_MS);

        /* Set PID values for each slot */
        setPID(Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_SLOT, Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_GAINS);
        setPID(Constants.DRIVEBASE_MOTIONMAGIC_TURN_SLOT, Constants.DRIVEBASE_MOTIONMAGIC_TURN_GAINS);

        zeroGyro();
        zeroDistance();

        swappedDrive = new Toggle();
        swappedDrive.setState(false);
    }

    private void setPID(final int slot, final MotionMagic gain) {
        ErrorCode eCode = ErrorCode.OK;

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
        ErrorCode eCode = ErrorCode.OK;
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

    public void drivePercentage(double driveLeft, double driveRight) {
        left.set(ControlMode.PercentOutput, driveLeft);
        right.set(ControlMode.PercentOutput, driveRight);
    }

    private void changeRemoteSensor(MotionMagicCommands command) {
        switch (command) {
        case FOWARD:
            right.configRemoteFeedbackFilter(left.getDeviceID(), RemoteSensorSource.TalonFX_SelectedSensor, 0,
                    Constants.DRIVEBASE_TIMEOUT_MS);
            break;
        case TURN:
            right.configRemoteFeedbackFilter(pidgey.getDeviceID(), RemoteSensorSource.Pigeon_Yaw,
                    Constants.DRIVEBASE_PIGEON_IMU_REMOTE_FILTER);
            break;
        }
    }

    public void driveMotionMagic(MotionMagicCommands command, double target, double optionalSensor) {
        switch (command) {
        case FOWARD:
            changeRemoteSensor(command);
            setSlot(Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_SLOT);
            right.set(ControlMode.MotionMagic, target);
            left.follow(right);
            break;
        case TURN:
            changeRemoteSensor(command);
            double targetSensor = optionalSensor * 4096 * 6;
            double targetTurn = target * 4096 * 6;

            setSlot(Constants.DRIVEBASE_MOTIONMAGIC_TURN_SLOT);
            right.set(ControlMode.MotionMagic, targetSensor, DemandType.AuxPID, targetTurn);
            left.follow(right);
            break;
        default:
            right.set(0);
            left.set(0);
            break;
        }
    }
}