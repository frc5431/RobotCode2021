package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;

public class Vision extends Component<Robot> {
    private Limelight front;
    private Toggle visionLightToggle = new Toggle();

    public Vision() {
        front = new Limelight(Constants.VISION_FRONT_LIMELIGHT);
        front.setLEDState(LEDState.ON);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if (visionLightToggle.getState()) {
            front.setLEDState(LEDState.ON);
        } else {
            front.setLEDState(LEDState.OFF);
        }
    }

    @Override
    public void disabled(Robot robot) {
    }

    public Limelight getFrontLimelight() {
        return front;
    }

    public Toggle getVisionLightToggle() {
        return visionLightToggle;
    }
}