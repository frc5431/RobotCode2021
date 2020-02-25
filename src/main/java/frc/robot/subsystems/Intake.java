package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Intake extends SubsystemBase {

    private WPI_TalonFX intakeMotor, pivotMotor;
    private Toggle intakeToggle, reverse, pivotToggle;
    private double intakeSpeed = Constants.INTAKE_DEFAULT_SPEED;
    private double pivotSpeed = Constants.PIVOT_DEFAULT_SPEED;

    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Intake() {
        intakeMotor = new WPI_TalonFX(Constants.INTAKE_ID);
        intakeMotor.setInverted(Constants.INTAKE_REVERSE);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRALMODE);

        pivotMotor = new WPI_TalonFX(Constants.PIVOT_ID);
        pivotMotor.setInverted(Constants.PIVOT_REVERSE);
        pivotMotor.setNeutralMode(Constants.PIVOT_NEUTRALMODE);

        intakeToggle = new Toggle();
        intakeToggle.setState(false);

        pivotToggle = new Toggle();
        pivotToggle.setState(false);

        reverse = new Toggle();
        reverse.setState(true);
    }
    
    @Override
    public void periodic() {
        intakeMotor.set(intakeSpeed);
        super.periodic();
    }

    public Toggle getIntakeToggle() {
        return intakeToggle;
    }

    /**
     * @return the pivotToggle
     */
    public Toggle getPivotToggle() {
        return pivotToggle;
    }

    /**
     * @return the controlMode
     */
    public ComponentControlMode getControlMode() {
        return controlMode;
    }

    /**
     * @param controlMode the controlMode to set
     */
    public void setControlMode(ComponentControlMode controlMode) {
        this.controlMode = controlMode;
    }

    /**
     * @return the intakeSpeed
     */
    public double getIntakeSpeed() {
        return intakeSpeed;
    }

    /**
     * @param intakeSpeed the intakeSpeed to set
     */
    public void setIntakeSpeed(double intakeSpeed) {
        this.intakeSpeed = intakeSpeed;
    }

    /**
     * @return the pivotSpeed
     */
    public double getPivotSpeed() {
        return pivotSpeed;
    }

    /**
     * @param pivotSpeed the pivotSpeed to set
     */
    public void setPivotSpeed(double pivotSeed) {
        pivotMotor.set(pivotSeed);
    }

    /**
     * @return the reverse
     */
    public Toggle getReverse() {
        return reverse;
    }
}