package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.robot.util.states.*;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Flywheel extends Component<Robot> {

    WPI_TalonFX flywheel, _flywheelFollow;

    Toggle flywheelToggle;
    double shooterSpeed;

    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Flywheel() {
        flywheel = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_LEFT_ID);
        _flywheelFollow = new WPI_TalonFX(Constants.SHOOTER_FLYWHEEL_RIGHT_ID);

        _flywheelFollow.follow(flywheel);

        // Set Inverted Mode
        flywheel.setInverted(Constants.SHOOTER_FLYWHEEL_REVERSE);
        _flywheelFollow.setInverted(InvertType.OpposeMaster); // Inverted via "!"

        assert (flywheel.getInverted() == !_flywheelFollow.getInverted());

        // Set Neutral Mode
        flywheel.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);
        _flywheelFollow.setNeutralMode(Constants.SHOOTER_FLYWHEEL_NEUTRALMODE);

        flywheel.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);
        // flywheelRight.configClosedloopRamp(Constants.SHOOTER_FLYWHEEL_RAMPING_SPEED);

        // Toggle Control
        flywheelToggle = new Toggle();
        flywheelToggle.setState(false);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if(controlMode == ComponentControlMode.MANUAL) {
            if (flywheelToggle.getState()) {
                flywheel.set(ControlMode.PercentOutput, shooterSpeed);
            } else {
                flywheel.set(ControlMode.PercentOutput, 0);
            }
        }

        assert (flywheel.get() == _flywheelFollow.get());
    }

    @Override
    public void disabled(Robot robot) {
    }

    public Toggle getFlywheelToggle() {
        return flywheelToggle;
    }

    public double getEncoderPosition() {
        return (_flywheelFollow.getSensorCollection().getIntegratedSensorVelocity());
    }

    public double getFlywheelSpeed() {
        return _flywheelFollow.get();
    }

    public void setShooterSpeed(double shooterSpeed) {
        this.shooterSpeed = shooterSpeed;
    }

    public void setVelocity(FlywheelState state) {
        flywheel.set(ControlMode.Velocity, state.getVelocity());
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
}