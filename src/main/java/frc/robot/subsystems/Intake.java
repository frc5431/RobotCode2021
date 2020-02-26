package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5431.titan.core.misc.Calc;;

public class Intake extends SubsystemBase {
    public static enum POSITION {
        UP(0), DOWN(1); // FIXME
        
        private double value;

        private POSITION(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
	}

    private WPI_TalonFX intakeMotor, pivotMotor;
    private POSITION position = POSITION.UP;

    public Intake() {
        intakeMotor = new WPI_TalonFX(Constants.INTAKE_ID);
        intakeMotor.setInverted(Constants.INTAKE_REVERSE);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRALMODE);

        pivotMotor = new WPI_TalonFX(Constants.PIVOT_ID);
        pivotMotor.setInverted(Constants.PIVOT_REVERSE);
        pivotMotor.setNeutralMode(Constants.PIVOT_NEUTRALMODE);
    }

    public void setIntakeFeedSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setPivotLocation(POSITION pos) {
        position = pos;
        // FIXME: Implement Motion Magic
    }

    public boolean atLocation() {
        double encoderValue = 0.0; // FIXME
        return Calc.approxEquals(position.getValue(), encoderValue, Constants.PIVOT_ERROR_RANGE);

    }
}