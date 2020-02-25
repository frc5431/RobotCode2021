package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Hopper extends SubsystemBase {

    WPI_TalonSRX hopperLeft, hopperRight;

    Toggle hopperToggle, reverseToggle;
    double hopperSpeed = 1.0; // TODO: Fine-tune hopper speed

    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Hopper() {
        hopperLeft = new WPI_TalonSRX(Constants.HOPPER_LEFT_ID);
        hopperRight = new WPI_TalonSRX(Constants.HOPPER_RIGHT_ID);

        hopperRight.follow(hopperLeft);

        hopperLeft.setInverted(Constants.HOPPER_REVERSE);
        hopperRight.setInverted(InvertType.OpposeMaster);

        hopperLeft.setNeutralMode(Constants.HOPPER_NEUTRALMODE);

        hopperToggle = new Toggle();
        hopperToggle.setState(false);

        reverseToggle = new Toggle();
        reverseToggle.setState(false);
    }

    @Override
    public void periodic() {
        if (hopperToggle.getState()) {
            if (!reverseToggle.getState())
                hopperLeft.set(hopperSpeed);
            else
                hopperLeft.set(-hopperSpeed);
        } else {
            hopperLeft.set(0);
        }
    }

    public Toggle getHopperToggle() {
        return hopperToggle;
    }

    public double getHopperSpeed() {
        return hopperSpeed;
    }

    public void setHopperSpeed(double hopperSpeed) {
        this.hopperSpeed = hopperSpeed;
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
     * @return the reverseToggle
     */
    public Toggle getReverse() {
        return reverseToggle;
    }
}