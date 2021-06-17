package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Ryan Hirasaki
 */
public class Intake extends SubsystemBase {
    private WPI_TalonFX intakeMotor;

    public Intake(WPI_TalonFX motor) {
        intakeMotor = motor;
        intakeMotor.setInverted(Constants.INTAKE_REVERSE);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRALMODE);

        intakeMotor.configFactoryDefault();
    }

    public void setSpeed(double speed) {
        // intakeMotor.set(ControlMode.PercentOutput, speed);
    }

    public List<WPI_TalonFX> getMotors() {
        return List.of(intakeMotor);
    }
}