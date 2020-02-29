package frc.robot.subsystems;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MotionMagic;
import frc.team5431.titan.core.misc.Calc;;

/**
 * @author Ryan Hirasaki
 */
public class Pivot extends SubsystemBase {
    public static enum POSITION {
        UP(.60), DOWN(-.60);

        private final double value;

        private POSITION(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    private WPI_TalonFX pivotMotor;
    private POSITION position = POSITION.UP;

    public Pivot() {
        pivotMotor = new WPI_TalonFX(Constants.PIVOT_ID);
        pivotMotor.setInverted(Constants.PIVOT_REVERSE);
        pivotMotor.setNeutralMode(Constants.PIVOT_NEUTRALMODE);

        pivotMotor.configFactoryDefault();

        // reset encoder
        pivotMotor.setSelectedSensorPosition(0);

        pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                Constants.DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE, Constants.DRIVEBASE_TIMEOUT_MS);

        setPID(0, new MotionMagic(0.05, 0, 0, 0, 0, 0, Constants.DRIVEBASE_TIMEOUT_MS));
    }

    private void setPID(final int slot, final MotionMagic gain) {
        ErrorCode eCode;

        eCode = pivotMotor.config_kP(slot, gain.kP);
        assert (eCode == ErrorCode.OK);

        eCode = pivotMotor.config_kI(slot, gain.kI);
        assert (eCode == ErrorCode.OK);

        eCode = pivotMotor.config_kD(slot, gain.kD);
        assert (eCode == ErrorCode.OK);

        eCode = pivotMotor.config_kF(slot, gain.kF);
        assert (eCode == ErrorCode.OK);

        eCode = pivotMotor.config_IntegralZone(slot, gain.kIntegralZone, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = pivotMotor.configClosedLoopPeakOutput(slot, gain.kPeakOutput, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);

        eCode = pivotMotor.configClosedLoopPeriod(slot, gain.kClosedLoopTime, Constants.DRIVEBASE_TIMEOUT_MS);
        assert (eCode == ErrorCode.OK);
    }

    // return the angle of the arm based on the current encoder value
    public double getAngle() {
        int currentPosition = pivotMotor.getSelectedSensorPosition();

        // 100:1

        // divide the encoder position when arm is horizontal by the angle displacement
        // so if you moved the arm 30 degrees and read 1000 ticks, it would be 1000/30
        // ticks per degree
        // subtract horizontalAngleDisplacement to make the horizontal postion 0 degrees
        // double ticksPerDegree = horizontalPosition / horizontalAngleDisplacement;
        double angle = (currentPosition * (2048 / 360)) / 100;
        return angle;
    }

    @Override
    public void periodic() {
        // Publish Pivot Encoder Position To Add To States
        SmartDashboard.putNumber("Pivot Position", pivotMotor.getSelectedSensorPosition());

        double angle = getAngle();
        double theta = Math.toRadians(angle);

        SmartDashboard.putNumber("Pivot Angle", angle);

        // get a range of 0 to 1 to multiply by feedforward.
        // when in horizontal position, value should be 1
        // when in vertical up or down position, value should be 0
        double gravityCompensation = Math.cos(theta);

        SmartDashboard.putNumber("Pivot Gravity Compensation", gravityCompensation);

        // horizontalHoldOutput is the minimum power required to hold the arm up when
        // horizontal
        // this is a range of 0-1, in our case it was .125 throttle required to keep the
        // arm up
        double feedForward = gravityCompensation * 0.125;

        SmartDashboard.putNumber("Pivot Feed Forward", feedForward);

    }

    public void setPivotLocation(POSITION pos) {
        position = pos;
        pivotMotor.set(ControlMode.PercentOutput, pos.getValue());
    }

    public boolean atLocation() {
        int encoderValue = pivotMotor.getSelectedSensorPosition();
        return Calc.approxEquals(position.getValue(), encoderValue, Constants.PIVOT_ERROR_RANGE);
    }
}