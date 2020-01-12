package frc.robot.components;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ControlPanelColors;
import frc.robot.util.ControlPanelStages;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.sensors.ColorSensor;

public class ColorWheel extends Component<Robot> {
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
        switch (robot.getControlPanelStage()) {
        case POSITIONAL:
            positional(robot);
            break;
        case ROTATIONAL:
            rotational(robot);
            break;
        default:
            break;
        }
    }

    @Override
    public void disabled(Robot robot) {
    }

    private void rotational(Robot robot) {
        if(true) {
            robot.setControlPanelStage(ControlPanelStages.POSITIONAL);
        }
    }

    private void positional(Robot robot) {
        ControlPanelColors currentColor = getSensorColor();
        ControlPanelColors gotoColor = getGoalSensorColor(getGameColor());

        if (currentColor == gotoColor) {
            // Stop Moving as we have reached the goal!
            // TODO: add stop motor code
            // TODO: add ability to redo positional
            robot.setControlPanelStage(ControlPanelStages.OFF);
            Logger.l("Color Goal has been Reached! Positional Code Says Goodbye!");
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

    private ControlPanelColors getGameColor() {
        /*
         * Code Borrowed from
         * https://docs.wpilib.org/en/latest/docs/software/wpilib-overview/2020-Game-
         * Data.html
         */
        ControlPanelColors data;
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            switch (gameData.charAt(0)) {
            case 'B':
                data = ControlPanelColors.BLUE;
                break;
            case 'G':
                data = ControlPanelColors.GREEN;
                break;
            case 'R':
                data = ControlPanelColors.RED;
                break;
            case 'Y':
                data = ControlPanelColors.YELLOW;
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

    private ControlPanelColors getSensorColor() {
        ControlPanelColors data;
        switch (colorSensor.findColorMatch()) {
        case 0:
            data = ControlPanelColors.RED;
            break;
        case 1:
            data = ControlPanelColors.GREEN;
            break;
        case 2:
            data = ControlPanelColors.BLUE;
            break;
        case 3:
            data = ControlPanelColors.YELLOW;
            break;
        default:
            data = null;
            break;
        }
        return data;
    }

    private ControlPanelColors getGoalSensorColor(ControlPanelColors color) {
        // Break if the input is null
        assert (color != null);

        ControlPanelColors goal;

        // Now This is Art!
        switch (color) {
        case RED:
            goal = ControlPanelColors.BLUE;
            break;
        case GREEN:
            goal = ControlPanelColors.YELLOW;
            break;
        case BLUE:
            goal = ControlPanelColors.RED;
            break;
        case YELLOW:
            goal = ControlPanelColors.GREEN;
            break;
        default:
            goal = null;
            break;
        }

        if (goal != null)
            Logger.l("Moving to %s", goal.getName());
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