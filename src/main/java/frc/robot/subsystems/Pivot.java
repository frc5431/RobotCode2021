package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.team5431.titan.core.misc.Calc;;

public class Pivot extends SubsystemBase {
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

    private WPI_TalonFX pivotMotor;
    private POSITION position = POSITION.UP;

    public Pivot() {
        pivotMotor = new WPI_TalonFX(Constants.PIVOT_ID);
        pivotMotor.setInverted(Constants.PIVOT_REVERSE);
        pivotMotor.setNeutralMode(Constants.PIVOT_NEUTRALMODE);

        pivotMotor.configFactoryDefault();
    }

    @Override
    public void periodic() {
        // Publish Pivot Encoder Position To Add To States
        SmartDashboard.putNumber("Pivot Position", pivotMotor.getSelectedSensorPosition());
    }
    public void setPivotLocation(POSITION pos) {
        position = pos;
        pivotMotor.set(ControlMode.Position, pos.getValue());       
    }

    public boolean atLocation() {
        int encoderValue = pivotMotor.getSelectedSensorPosition(); // FIXME
        return Calc.approxEquals(position.getValue(), encoderValue, Constants.PIVOT_ERROR_RANGE);
    }
}