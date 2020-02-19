package frc.robot.components;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Hopper extends Component<Robot> {

    WPI_TalonSRX hopperLeft, hopperRight;

    Toggle hopperToggle;
    double hopperSpeed = 0.5; // TODO: Fine-tune hopper speed

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
    }

    @Override
    public void init(final Robot robot) {
    }

    @Override
    public void periodic(final Robot robot) {
        if (hopperToggle.getState()) {
            hopperLeft.set(hopperSpeed);
        } else {
            hopperLeft.set(0);
        }
    }

    @Override
    public void disabled(final Robot robot) {
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
}