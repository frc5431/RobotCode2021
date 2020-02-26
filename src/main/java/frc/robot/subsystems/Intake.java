package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Ryan Hirasaki
 */
public class Intake extends SubsystemBase {
    private WPI_TalonFX intakeMotor;

    public Intake() {
        intakeMotor = new WPI_TalonFX(Constants.INTAKE_ID);
        intakeMotor.setInverted(Constants.INTAKE_REVERSE);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRALMODE);

        intakeMotor.configFactoryDefault();
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }
}