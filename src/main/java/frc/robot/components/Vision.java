package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.auto.commands.Targetor;
import frc.robot.auto.vision.TargetType;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.misc.Toggle;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.Command.CommandResult;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;

public class Vision extends Component<Robot> {
    private Limelight front;
    private Toggle visionLightToggle = new Toggle();
    private Toggle targetToggle = new Toggle();
    private boolean targetLocked = false;

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
        if (targetToggle.getState()){
            targetLocked = target(robot, TargetType.SHIELDGENERATOR);
            if (targetLocked) {
                targetToggle.setState(false);
            }
        } else {
            targetLocked = false;
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

    public Toggle getTargetToggle() {
        return targetToggle;
    }

    private boolean target(Robot robot, TargetType type) {
        final Drivebase drivebase = robot.getDrivebase();

        front.getTable().getEntry("pipeline").setNumber(type.getPipeline());

        final double x = front.getX();
        final double y = front.getY();

        final boolean xInRange = Calc.approxEquals(x, 0, Constants.LIMELIGHT_ERROR_RATE);
        final boolean yInRange = Calc.approxEquals(y, 0, Constants.LIMELIGHT_ERROR_RATE);

        // If x and y are bad, then fix
        if ((!xInRange) && (!yInRange)) {
            drivebase.drivePercentageArcade(y, x); // TODO: Test the error, most likely needs to multiplied.
            return false;
        }
        // if only x is bad then fix x
        else if (!xInRange && yInRange) {
            drivebase.drivePercentageArcade(0, x); // TODO: Test the error, most likely needs to multiplied.
            return false;
        }
        // if only y is bad then fix y
        else if (xInRange && !yInRange) {
            drivebase.drivePercentageArcade(y, 0); // TODO: Test the error, most likely needs to multiplied.
            return false;
        } else {
            return true;
        }
    }
}