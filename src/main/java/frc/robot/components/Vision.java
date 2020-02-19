package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.auto.commands.Targetor;
import frc.robot.auto.vision.TargetType;
import frc.team5431.titan.core.joysticks.Xbox;
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
        // front.setLEDState(LEDState.ON);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        if (visionLightToggle.getState()) {
            // front.setLEDState(LEDState.ON);
        } else {
            // front.setLEDState(LEDState.OFF);
        }
        if (targetToggle.getState()) {
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
        boolean overriden = isOverridden(robot);
        if (overriden)
            return true;
        final Drivebase drivebase = robot.getDrivebase();

        front.getTable().getEntry("pipeline").setNumber(type.getPipeline());

        double x = front.getX();
        
        //MOVE TO CONSTANTS LATER YO
        final double kP = 0.03;
        final double MIN_COMMAND = 0.04;

        double headingError = -x;
        double steeringAdjust = 0;

        if (x > 1.0) {
            steeringAdjust = kP * headingError - MIN_COMMAND;
        } else if (x < 1.0) {
            steeringAdjust = kP * headingError + MIN_COMMAND;
        }


        final boolean xInRange = Calc.approxEquals(x, 0, Constants.LIMELIGHT_ERROR_RATE);
        //final boolean yInRange = Calc.approxEquals(y, 0, Constants.LIMELIGHT_ERROR_RATE);
        
        if (!xInRange) {
            drivebase.drivePercentageArcade(0, steeringAdjust);
            return false;
        } else {
            drivebase.drivePercentageArcade(0, 0);
            return true;
        }

        /*
        // If x and y are bad, then fix
        if ((!xInRange) && (!yInRange)) {
            drivebase.drivePercentageArcade(y, x); // TODO: Test the error, most likely needs to multiplied.
            return false;
        }
        //if only x is bad then fix x
        if (!xInRange && yInRange) {
            drivebase.drivePercentageArcade(0, x); // TODO: Test the error, most likely needs to multiplied.
            return false;
        }
        // if only y is bad then fix y
        else if (xInRange && !yInRange) {
            drivebase.drivePercentageArcade(y, 0); // TODO: Test the error, most likely needs to multiplied.
            return false;
        } else {
            return true;
        } */
    }


    /**
     * @author Daniel Brubaker
     * @param robot
     * @return returns whether joystick is active (and should be overriding Vision) 
     */

    //checks if the joysticks are being significantly moved on either drive type, on any direction
    //if they are being moved, it returns true so it can override vision movement
    public boolean isOverridden(Robot robot) {
        final Xbox driver = robot.getTeleop().getDriver();

        //sets new deadzone, adds a small value so this function will not override if the joystick is slightly bumped
        final double deadzone = driver.getDeadzoneMax() + 0.2;

        //gets whether the control stick has been moved over the new deadzone in each direction
        //rightX is not gotten because it is not used in either tank or arcade :'(
        boolean leftYActive = Math.abs(driver.getRawAxis(Xbox.Axis.LEFT_Y)) >= deadzone;
        boolean leftXActive = Math.abs(driver.getRawAxis(Xbox.Axis.LEFT_X)) >= deadzone;
        boolean rightYActive = Math.abs(driver.getRawAxis(Xbox.Axis.RIGHT_Y)) >= deadzone;

        //gets selected drivetype, checks if active, and returns
        switch (robot.getDashboard().getSelectedDriveType()) {
        case ARCADE:
            if (leftXActive || leftYActive) {
                //joystick moved in arcade
                return true;
            }
            break;
        case TANK: 
            if (rightYActive || leftYActive) {
                //joystick moved in tank
                return true;
            }
            break;
        }
        //no joystick active
        return false;
    }
}