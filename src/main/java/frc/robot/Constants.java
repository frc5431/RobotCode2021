package frc.robot;

import frc.robot.util.RobotType;

public final class Constants {
    public static final RobotType ROBOT_TYPE = RobotType.PRACTICE;

    // TODO: Calibrate Sensor for dummy colors at test field
    public static final double[] PRAC_YELLOW = { 0, 0, 0 };
    public static final double[] PRAC_RED = { 0, 0, 0 };
    public static final double[] PRAC_GREEN = { 0, 0, 0 };
    public static final double[] PRAC_BLUE = { 0, 0, 0 };

    // TODO: Calibrate Sensor for competition as Competition Values from rulebook
    public static final double[] COMP_YELLOW = { 1, 1, 0 };
    public static final double[] COMP_RED = { 1, 0, 0 };
    public static final double[] COMP_GREEN = { 0, 1, 0 };
    public static final double[] COMP_BLUE = { 0, 1, 1 };

    // This code will automaticly change which color profile to use depending if at
    // competition or at the school.
    public static final double[] YELLOW = (ROBOT_TYPE == RobotType.COMPETITION) ? COMP_YELLOW : PRAC_YELLOW;
    public static final double[] RED = (ROBOT_TYPE == RobotType.COMPETITION) ? COMP_RED : PRAC_RED;
    public static final double[] GREEN = (ROBOT_TYPE == RobotType.COMPETITION) ? COMP_GREEN : PRAC_GREEN;
    public static final double[] BLUE = (ROBOT_TYPE == RobotType.COMPETITION) ? COMP_BLUE : PRAC_BLUE;

    public static final double CONTROLPANEL_CONFIDENCE = 0.75;

}