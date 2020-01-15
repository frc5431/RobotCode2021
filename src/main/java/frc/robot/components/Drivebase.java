package frc.robot.components;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.MotionMagic;
import frc.team5431.titan.core.robot.Component;

public class Drivebase extends Component<Robot> {
    public static enum MotorMode {
        MOTIONMAGIC, PERCENTAGE
    }

    private WPI_TalonFX left;
    private WPI_TalonFX right;

    private BaseMotorController _leftFollower;
    private BaseMotorController _rightFollower;

    public Drivebase() {
        left = new WPI_TalonFX(Constants.DRIVEBASE_LEFT_FRONT_ID);
        _leftFollower = new WPI_TalonFX(Constants.DRIVEBASE_LEFT_BACK_ID);

        right = new WPI_TalonFX(Constants.DRIVEBASE_RIGHT_FRONT_ID);
        _rightFollower = new WPI_TalonFX(Constants.DRVIEBASE_RIGHT_BACK_ID);

        _leftFollower.configFactoryDefault();
        _rightFollower.configFactoryDefault();

        _leftFollower.follow(left);
        _rightFollower.follow(right);

        left.setInverted(Constants.DRIVEBASE_LEFT_REVERSE);
        right.setInverted(Constants.DRIVEBASE_RIGHT_REVERSE);

        String[] motors = { "Left", "Right" };

        for (String motorString : motors) {
            MotionMagic magic;
            WPI_TalonFX motor;

            assert (Arrays.asList(motors).stream().anyMatch(x -> x.equals(motorString)));

            if (motorString.equalsIgnoreCase("left")) {
                magic = Constants.DRIVEBASE_MOTIONMAGIC_LEFT;
                motor = left;
            } else if (motorString.equalsIgnoreCase("right")) {
                magic = Constants.DRIVEBASE_MOTIONMAGIC_RIGHT;
                motor = right;
            } else {
                magic = null;
                motor = null;
                assert (false);
                // This Code should not ever be reached due to the assert above
            }

            motor.configFactoryDefault();

            motor.config_kF(magic.kSlotId, magic.kF);
            motor.config_kP(magic.kSlotId, magic.kP);
            motor.config_kI(magic.kSlotId, magic.kI);
            motor.config_kD(magic.kSlotId, magic.kD);

            motor.configNeutralDeadband(0.001, magic.kTimeoutMs);

            motor.configNominalOutputForward(0, magic.kTimeoutMs);
            motor.configNominalOutputReverse(0, magic.kTimeoutMs);
            motor.configPeakOutputForward(1, magic.kTimeoutMs);
            motor.configPeakOutputReverse(-1, magic.kTimeoutMs);

            motor.setSelectedSensorPosition(0, magic.kPIDLoopIdx, magic.kSlotId);
        }
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
    }

    @Override
    public void disabled(Robot robot) {
    }

    private void driveMotor(WPI_TalonFX motor, MotorMode mode, double value) {
        assert (motor != null);

        switch (mode) {
        case MOTIONMAGIC:
            motor.set(ControlMode.MotionMagic, value);
            break;
        case PERCENTAGE:
            motor.set(ControlMode.PercentOutput, value);
            break;
        }
        left.set(value);
    }

    public void drive(MotorMode mode, double left, double right) {
        /*
         * Multithread this for that nanosecond of performance increace!!! Not really.
         * This Code is used to both set the speed of the left and right sode of the
         * drivebase.
         */
        driveMotor(this.left, mode, left);
        driveMotor(this.right, mode, right);
    }
}