package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.vision.Limelight;

public class Vision extends Component<Robot> {
    private Limelight front;

    public Vision() {
        front = new Limelight(Constants.VISION_FRONT_LIMELIGHT);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
    }

    @Override
    public void disabled(Robot robot) {
    }

}