package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5431.titan.core.misc.Calc;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

/**
 * @author Colin Wong
 * @author Rishmita Rao
 * @author Daniel Brubaker
 * @author Ryan Hirasaki
 */
public class Pivot extends SubsystemBase {
    public static enum POSITION {
        UP(Constants.PIVOT_UP_LIMIT), DOWN(Constants.PIVOT_DOWN_LIMIT), ZERO(0);

        private final double value;

        private POSITION(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    public static enum SPEED {
        SPEED_UP(Constants.PIVOT_DEFAULT_SPEED), SPEED_DOWN(-Constants.PIVOT_DEFAULT_SPEED), ZERO(0);

        private final double value;

        private SPEED(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }


    private WPI_TalonFX pivotMotor;
    private POSITION position = POSITION.UP;

	private PIDController pidController;
	private final PowerDistributionPanel pdp;


    public Pivot(PowerDistributionPanel pdp, WPI_TalonFX pivotMotor) {
		this.pdp = pdp;
        this.pivotMotor = pivotMotor;
        this.pivotMotor.setInverted(Constants.PIVOT_REVERSE);
        this.pivotMotor.setNeutralMode(Constants.PIVOT_NEUTRALMODE);
        this.pivotMotor.configFactoryDefault();
        
        // reset encoder
        this.pivotMotor.setSelectedSensorPosition(0);
        this.pivotMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.SLOT_0,
                Constants.DRIVEBASE_TIMEOUT_MS);

        // flywheel.setSensorPhase(true);

        this.pivotMotor.configPeakOutputForward(Constants.PIVOT_DEFAULT_SPEED);
        this.pivotMotor.configPeakOutputReverse(-Constants.PIVOT_DEFAULT_SPEED);


        this.pivotMotor.config_kF(Constants.SLOT_0, Constants.PIVOT_MOTION_MAGIC.kF, Constants.DRIVEBASE_TIMEOUT_MS);
        this.pivotMotor.config_kP(Constants.SLOT_0, Constants.PIVOT_MOTION_MAGIC.kP, Constants.DRIVEBASE_TIMEOUT_MS);
        this.pivotMotor.config_kI(Constants.SLOT_0, Constants.PIVOT_MOTION_MAGIC.kI, Constants.DRIVEBASE_TIMEOUT_MS);
        this.pivotMotor.config_kD(Constants.SLOT_0, Constants.PIVOT_MOTION_MAGIC.kD, Constants.DRIVEBASE_TIMEOUT_MS);
    }

    

    // return the angle of the arm based on the current encoder value
    // public double getAngle() {
    //     int currentPosition = pivotMotor.getSelectedSensorPosition();

    //     // 100:1

    //     // divide the encoder position when arm is horizontal by the angle displacement
    //     // so if you moved the arm 30 degrees and read 1000 ticks, it would be 1000/30
    //     // ticks per degree
    //     // subtract horizontalAngleDisplacement to make the horizontal postion 0 degrees
    //     // double ticksPerDegree = horizontalPosition / horizontalAngleDisplacement;
    //     double angle = (currentPosition * (2048 / 360)) / 100;
    //     return angle;
    // }

    @Override
    public void periodic() {
        // Publish Pivot Encoder Position To Add To States
        SmartDashboard.putNumber("Pivot Position", pivotMotor.getSelectedSensorPosition());

        // double angle = getAngle();
        // double theta = Math.toRadians(angle);

        // SmartDashboard.putNumber("Pivot Angle", angle);

        // // get a range of 0 to 1 to multiply by feedforward.
        // // when in horizontal position, value should be 1
        // // when in vertical up or down position, value should be 0
        // double gravityCompensation = Math.cos(theta);

        // SmartDashboard.putNumber("Pivot Gravity Compensation", gravityCompensation);

        // // horizontalHoldOutput is the minimum power required to hold the arm up when
        // // horizontal
        // // this is a range of 0-1, in our case it was .125 throttle required to keep the
        // // arm up
        // double feedForward = gravityCompensation * 0.125;

        // SmartDashboard.putNumber("Pivot Feed Forward", feedForward);

        SmartDashboard.putNumber("Pivot Sensor Velocity", pivotMotor.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Pivot Speed", pivotMotor.get());
		SmartDashboard.putNumber("Pivot Error Rate", pivotMotor.getClosedLoopError(Constants.SLOT_0));
		SmartDashboard.putNumber("Pivot current", pdp.getCurrent(Constants.PIVOT_PDP_SLOT));
		if(4 <= pdp.getCurrent(Constants.PIVOT_PDP_SLOT)) {
			// Slow down pivot
			pivotMotor.set(0);
		}

    }

    public void setPivotLocation(POSITION pos) {
        // setSpeed(pos.getValue());
        int horizontal = Constants.PIVOT_DOWN_LIMIT;
        double ticksToDegrees = (2048 / 360) / 81;
        double curentPosition = getEncoderPosition();
        double degrees = (curentPosition - horizontal) / ticksToDegrees;
        double radians = java.lang.Math.toRadians(degrees);
        double CosineScalar = Math.cos(radians);
        double maxGravity = Constants.PIVOT_AFFECT_GRAVITY;

        pivotMotor.set(ControlMode.Position, pos.getValue(), DemandType.ArbitraryFeedForward ,CosineScalar * maxGravity);
    }

    public void setSpeed(Pivot.SPEED speed) {
        pivotMotor.set(ControlMode.PercentOutput, speed.getValue());
    }

    private double getEncoderPosition() {
        return pivotMotor.getSelectedSensorPosition(Constants.SLOT_0);
    }

    public boolean atLocation() {
        double encoderValue = pivotMotor.getSelectedSensorPosition();
        return Calc.approxEquals(position.getValue(), encoderValue, Constants.PIVOT_ERROR_RANGE);
    }

    public PIDController getPidController() {
        return pidController;
    }

	public void reset() {
        pivotMotor.setSelectedSensorPosition(0);
	}

	public double error() {
		return pivotMotor.getClosedLoopError();
    }
    
    public List<WPI_TalonFX> getMotors() {
        return List.of(pivotMotor);
    }
}