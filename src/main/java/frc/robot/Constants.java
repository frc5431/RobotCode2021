package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.util.MotionMagic;
import frc.robot.util.RobotType;

public final class Constants {
    public static final RobotType ROBOT_TYPE = RobotType.PRACTICE;

    // ================================================================================
    // Teleop Controller Data
    // ================================================================================

    /*
     * Controller name does not need to be the enitre string but must contain a word
     * from the HID name.
     * 
     * Capitalization does not matter. All strings shoud be translated to lowercase
     * by the software. Setting the the name of the controller is for safty as you
     * do not want a secondary controller accidentally controlling the robot when it
     * may have a different layout.
     */

    public static final int DRIVER_XBOX_ID = 0;
    public static final double DRIVER_XBOX_DEADZONE = 0.10;
    public static final String DRIVER_XBOX_NAME = "xbox 360";

    public static final int OPERATOR_LOGITECH_ID = 1;
    public static final double OPERATOR_LOGITECH_DEADZONE = 0.10;
    public static final String OPERATOR_LOGITECH_NAME = "logitech";

    // ================================================================================
    // Motor ID`s and Reverse State
    // ================================================================================

    // Drivebase Related
    public static final int DRIVEBASE_FRONT_LEFT_ID = 1;
    public static final int DRIVEBASE_BACK_LEFT_ID = 3;
    public static final boolean DRIVEBASE_LEFT_REVERSE = false;

    public static final int DRIVEBASE_FRONT_RIGHT_ID = 2;
    public static final int DRIVEBASE_BACK_RIGHT_ID = 4;
    public static final boolean DRIVEBASE_RIGHT_REVERSE = true;

    public static final NeutralMode DRIVEBASE_NEUTRAL_MODE = NeutralMode.Brake;

    // Climber Related
    public static final int CLIMBER_BALANCER_ID = 5;
    public static final boolean CLIMBER_BALANCER_REVERSE = false;

    public static final int CLIMBER_ELEVATOR_ID = 6;
    public static final boolean CLIMBER_ELEVATOR_REVERSE = false;

    // Shooter Flywheel Related
    public static final int SHOOTER_FLYWHEEL_LEFT_ID = 7;
    public static final boolean SHOOTER_FLYWHEEL_LEFT_REVERSE = true;

    public static final int SHOOTER_FLYWHEEL_RIGHT_ID = 8;
    public static final boolean SHOOTER_FLYWHEEL_RIGHT_REVERSE = false;

    public static final NeutralMode SHOOTER_FLYWHEEL_NEUTRALMODE = NeutralMode.Coast;

    // Shooter Feeder Related
    public static final int SHOOTER_FEEDER_ID = 9;
    public static final boolean SHOOTER_FEEDER_REVERSE = false;
    public static final NeutralMode SHOOTER_FEEDER_NEUTRALMODE = NeutralMode.Brake;

    // Intake Related
    public static final int INTAKE_ID = 10;
    public static final boolean INTAKE_REVERSE = false;
    public static final NeutralMode INTAKE_NEUTRALMODE = NeutralMode.Brake;

    // Control Panel Related
    public static final int CONTROLPANEL_CANTALON_ID = 0;
    public static final boolean CONTROLPANEL_CANTALON_REVERSE = false;

    // ================================================================================
    // Drive Base Motion Magic
    // ================================================================================

    public static final int DRIVEBASE_TIMEOUT_MS = 30;

    public static final int SLOT_0 = 0;
    public static final int SLOT_1 = 1;
    public static final int SLOT_2 = 2;
    public static final int SLOT_3 = 3;

    // TODO: Set Proper PID Values
    // P, I, D, F, INTERGRAL, PEAKOUTPUT, CLOSEDLOOPTIME_MS
    public static final MotionMagic DRIVEBASE_MOTIONMAGIC_DRIVE_GAINS = new MotionMagic(0.2, 0, 0, 0, 100, 1, 1);
    public static final MotionMagic DRIVEBASE_MOTIONMAGIC_TURN_GAINS = new MotionMagic(0.2, 0, 0, 0, 100, 1, 1);
    public static final int DRIVEBASE_MOTIONMAGIC_DRIVE_SLOT = SLOT_0;
    public static final int DRIVEBASE_MOTIONMAGIC_TURN_SLOT = SLOT_1;

    // ================================================================================
    // Control Panel Specific
    // ================================================================================

    public static final double CONTROLPANEL_MOTOR_WHEEL_DIAMETER_FEET = 1;
    public static final double CONTROLPANEL_ROTATIONS = 4;

    public static final double CONTROLPANEL_ENCODER_PULSES_PER_ROTATION = 256.0;
    public static final int CONTROLPANEL_ENCODER_SOURCE_A = 0;
    public static final int CONTROLPANEL_ENCODER_SOURCE_B = 1;

    public static final double CONTROLPANEL_CONFIDENCE = 0.75;

    // Color Sensor Calibration (RGB)

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

}