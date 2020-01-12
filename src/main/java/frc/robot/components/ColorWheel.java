package frc.robot.components;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.sensors.ColorSensor;

public class ColorWheel extends Component<Robot> {
    private enum ColorFound {
        RED, GREEN, BLUE, YELLOW
    }

    private Color[] colors = { ColorMatch.makeColor(Constants.RED[0], Constants.RED[1], Constants.RED[2]),
            ColorMatch.makeColor(Constants.GREEN[0], Constants.GREEN[1], Constants.GREEN[2]),
            ColorMatch.makeColor(Constants.BLUE[0], Constants.BLUE[1], Constants.BLUE[2]),
            ColorMatch.makeColor(Constants.YELLOW[0], Constants.YELLOW[1], Constants.YELLOW[2]) };

    private ColorSensor colorSensor;

    @Override
    public void init(Robot robot) {
        colorSensor = new ColorSensor(Constants.CONTROLPANEL_CONFIDENCE, colors);
    }

    @Override
    public void periodic(Robot robot) {
        ColorFound currentColor = getSensorColor();
        ColorFound gotoColor = getGoalSensorColor(getGameColor());

        if (currentColor == gotoColor) {
            // Stop Moving as we have reached the goal!
            // TODO: add stop motor code
            Logger.l("Color Goal has been Reached!\nSkipping Color Wheen Code until FMS changes color.");
        }

        // To anyone looking in the future the wheel in this scenario is the object we
        // are moving not the robot wheel. Read the comments in the switch statement
        // with this knowlege
        switch (currentColor) {
        case RED:
            switch (gotoColor) {
            case RED:
                return;
            case YELLOW:
                turnWheel(false);
                break;
            case BLUE:
                turnWheel(true);
                break;
            case GREEN:
                turnWheel(true);
                break;
            }
            // Red to green cw
            // Red to yellow ccw
            // Red to blue cc or ccw
            break;

        case YELLOW:
            switch (gotoColor) {
            case RED:
                turnWheel(true);
                break;
            case YELLOW:
                return;
            case BLUE:
                turnWheel(false);
                break;
            case GREEN:
                turnWheel(true);
                break;
            }
            // Yellow to red cw
            // Yellow to blue ccw
            // Yellow to green cw or ccw
            break;

        case BLUE:
            switch (gotoColor) {
            case RED:
                turnWheel(true);
                break;
            case YELLOW:
                turnWheel(true);
                break;
            case BLUE:
                return;
            case GREEN:
                turnWheel(false);
                break;
            }
            // Blue to yellow cw
            // Blue to green ccw
            // Blue to red cw or ccw
            break;

        case GREEN:
            switch (gotoColor) {
            case RED:
                turnWheel(false);
                break;
            case YELLOW:
                turnWheel(true);
                break;
            case BLUE:
                turnWheel(true);
                break;
            case GREEN:
                return;
            }
            // Green to blue cw
            // Green to red ccw
            // Green to yellow cw or ccw
            break;

        default:
            return;
        }

    }

    @Override
    public void disabled(Robot robot) {
    }

    private ColorFound getGameColor() {
        /*
         * Code Borrowed from
         * https://docs.wpilib.org/en/latest/docs/software/wpilib-overview/2020-Game-
         * Data.html
         */
        ColorFound data;
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            switch (gameData.charAt(0)) {
            case 'B':
                data = ColorFound.BLUE;
                break;
            case 'G':
                data = ColorFound.GREEN;
                break;
            case 'R':
                data = ColorFound.RED;
                break;
            case 'Y':
                data = ColorFound.YELLOW;
                break;
            default:
                data = null;
                break;
            }
        } else {
            data = null;
        }

        return data;
    }

    private ColorFound getSensorColor() {
        ColorFound data;
        switch (colorSensor.findColorMatch()) {
        case 0:
            data = ColorFound.RED;
            break;
        case 1:
            data = ColorFound.GREEN;
            break;
        case 2:
            data = ColorFound.BLUE;
            break;
        case 3:
            data = ColorFound.YELLOW;
            break;
        default:
            data = null;
            break;
        }
        return data;
    }

    private ColorFound getGoalSensorColor(ColorFound color) {
        // Break if the input is null
        assert (color != null);

        ColorFound goal;
        String goalStr;

        // Now This is Art!
        switch (color) {
        case RED:
            goal = ColorFound.BLUE;
            goalStr = "Blue";
            break;
        case GREEN:
            goal = ColorFound.YELLOW;
            goalStr = "Yellow";
            break;
        case BLUE:
            goal = ColorFound.RED;
            goalStr = "Red";
            break;
        case YELLOW:
            goal = ColorFound.GREEN;
            goalStr = "Green";
            break;
        default:
            goal = null;
            goalStr = "";
            break;
        }

        if (goal != null)
            Logger.l("Moving to %s", goalStr);
        return goal;
    }

    private void turnWheel(boolean cw) {

        // To anyone looking in the future the wheel in this scenario is the object we
        // are moving not the robot wheel. Read the comments in the switch statement
        // with this knowlege

        // We do not need to worry about slowing down with PID as the wheel is slow to
        // turn at max speed on a 775. the logic in the periodic function will be able
        // to stop the motor.

        if (cw) {
            // Turn Wheel 45 degrees clockwise
            // TODO: Add Code to spin the motor counter clockwise
        } else {
            // Turn Wheel 45 degrees counter clockwise
            // TODO: Add Code to spin the motor clockwise
        }

    }
}