package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;

public class Intake extends Component<Robot> {

    private WPI_TalonFX intakeMotor;
    private Toggle toggle;

    private ComponentControlMode controlMode = ComponentControlMode.MANUAL;

    public Intake() {
        intakeMotor = new WPI_TalonFX(Constants.INTAKE_ID);
        intakeMotor.setInverted(Constants.INTAKE_REVERSE);
        intakeMotor.setNeutralMode(Constants.INTAKE_NEUTRALMODE);

        toggle = new Toggle();
        toggle.setState(false);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if (toggle.getState()) {
            intakeMotor.set(Constants.INTAKE_SPEED);
        } else {
            intakeMotor.set(0);
        }
    }

    @Override
    public void disabled(Robot robot) {
    }

    public Toggle getToggle() {
        return toggle;
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