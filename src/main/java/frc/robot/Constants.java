package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.util.MotionMagic;
import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Haha, the names are there because literally everyone on programming has touched Constants.
 * 
 * @author Ryan Hirasaki
 * @author Colin Wong
 * @author Rishmita Rao
 * @author Daniel Brubaker
 * @author Albert Ma
 * @author Jyrell Go
 * @author Isabella Diaz
 * @author Carlo Greenwell
 * @author Noah Markman
 * @author Tauseef Afraz
 */
public final class Constants {

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
    public static final double DRIVER_XBOX_DEADZONE = 0.15;
    public static final String DRIVER_XBOX_NAME = "xbox";

    public static final int OPERATOR_LOGITECH_ID = 1;
    public static final double OPERATOR_LOGITECH_DEADZONE = 0.10;
    public static final String OPERATOR_LOGITECH_NAME = "logitech";

    public static final int DRIVEBASE_TIMEOUT_MS = 30;

    public static final int SLOT_0 = 0;
    public static final int SLOT_1 = 1;
    public static final int SLOT_2 = 2;
    public static final int SLOT_3 = 3;

    public static final int REMOTE_0 = 0;
    public static final int REMOTE_1 = 1;

    // ================================================================================
    // Motor ID`s and Reverse State
    // ================================================================================

    // Drivebase Related
    public static final int DRIVEBASE_FRONT_LEFT_ID = 1;
    public static final int DRIVEBASE_BACK_LEFT_ID = 4;
    public static final boolean DRIVEBASE_LEFT_REVERSE = false;

    public static final int DRIVEBASE_FRONT_RIGHT_ID = 3;
    public static final int DRIVEBASE_BACK_RIGHT_ID = 2;
	public static final boolean DRIVEBASE_RIGHT_REVERSE = true;
	
	public static final double DRIVEBASE_TURN_MAX_SPEED = 0.35;

    public static final NeutralMode DRIVEBASE_NEUTRAL_MODE = NeutralMode.Brake;

    public static final double DRIVEBASE_DEFAULT_RAMPING = 0.6;

    // Climber Related
    public static final int CLIMBER_BALANCER_ID = 13;
    public static final boolean CLIMBER_BALANCER_REVERSE = false;

    public static final int CLIMBER_ELEVATOR_ID = 6;
    public static final boolean CLIMBER_ELEVATOR_REVERSE = true;
    public static final NeutralMode CLIMBER_ELEVATOR_NEUTRALMODE = NeutralMode.Brake;
    
	public static final int CLIMBER_ELEVATOR_UPPER_LIMIT = 740000;
	public static final int CLIMBER_ELEVATOR_LOWER_LIMIT = 1000;

    // Shooter Flywheel Related
    public static final int SHOOTER_FLYWHEEL_LEFT_ID = 10;
    public static final int SHOOTER_FLYWHEEL_RIGHT_ID = 11;

    public static final boolean SHOOTER_FLYWHEEL_REVERSE = true;

    public static final NeutralMode SHOOTER_FLYWHEEL_NEUTRALMODE = NeutralMode.Brake;
	public static final double SHOOTER_FLYWHEEL_RAMPING_SPEED = 0.2500;
	
	public static final double FLYWHEEL_VELOCITY_RANGE = 350;
    
    public static final int SHOOTER_FLYWHEEL_VELOCITY_HIGH = 11300;
    public static final double SHOOTER_FLYWHEEL_SPEED_HIGH = 0.585; // .585

    public static final int SHOOTER_FLYWHEEL_VELOCITY_LOW = 10500; //11900
    public static final double SHOOTER_FLYWHEEL_SPEED_LOW = 0.4;

    public static final MotionMagic SHOOTER_FLYWHEEL_GAINS = new MotionMagic(0, 0, 0, 0.053); //0.0474073170731707, 0.004

    // Shooter Feeder Related
    public static final int SHOOTER_FEEDER_ID = 9;
    public static final boolean SHOOTER_FEEDER_REVERSE = true;
    public static final NeutralMode SHOOTER_FEEDER_NEUTRALMODE = NeutralMode.Brake;
	public static final double SHOOTER_FEEDER_DEFAULT_SPEED = 0.7;
	public static final double SHOOTER_FEEDER_FAR_DEFAULT_SPEED = 0.5;


    public static final long SHOOTER_FEEDER_BALL_DELAY = 1300;
	public static final long SHOOTER_FEEDER_UP_DELAY = 1000;
	public static final long SHOOTER_FEEDER_DOWN_DELAY = 200;

    // Intake Related
    public static final int INTAKE_ID = 5;
    public static final boolean INTAKE_REVERSE = false;
    public static final NeutralMode INTAKE_NEUTRALMODE = NeutralMode.Brake;
    public static final double INTAKE_DEFAULT_SPEED = 1.0;

    // Pivot related
    public static final int PIVOT_ID = 12; // REMINDER: CHANGE TO 12 LATER
    public static final boolean PIVOT_REVERSE = false;
    public static final NeutralMode PIVOT_NEUTRALMODE = NeutralMode.Coast;
    public static final double PIVOT_DEFAULT_SPEED = 0.2;
    public static final int PIVOT_DOWN_LIMIT = (-50200 / 100) * 81; // 45000
    public static final int PIVOT_UP_LIMIT = (-800 / 100) * 81; // 5000 
    public static final int PIVOT_VELOCITY = 5000;
    public static final double PIVOT_AFFECT_GRAVITY = -0.02;

    public static final int PIVOT_PID_SLOT = SLOT_0;
    // public static final MotionMagic PIVOT_MOTION_MAGIC = new MotionMagic(0.1023, 0.0, 0, 0);
    public static final MotionMagic PIVOT_MOTION_MAGIC = new MotionMagic(0.1023, 0.0, 0, 0);
    // public static final MotionMagic PIVOT_MOTION_MAGIC = new MotionMagic(0.0, 0.0, 0.0, 0.0);

    // Hopper   
    public static final int HOPPER_LEFT_ID = 7;
    public static final int HOPPER_RIGHT_ID = 8;
    public static final boolean HOPPER_REVERSE = false;
    public static final NeutralMode HOPPER_NEUTRALMODE = NeutralMode.Coast;
    public static final double HOPPER_LEFT_SPEED = 0.85;
    public static final double HOPPER_RIGHT_SPEED = 0.7;
    
    // ================================================================================
    // Vision Data
    // ================================================================================

    public static final String VISION_FRONT_LIMELIGHT = "limelight";
    public static final int LIMELIGHT_PIPELINE_OFF = 9;
    public static final int LIMELIGHT_PIPELINE_ON = 0;
	public static final PIDController LIMELIGHT_PID = new PIDController(0.046, 0.002, 0);
	public static final double LIMELIGHT_ERROR_RATE = 0.03;
	public static final double LIMELIGHT_ASPECT_RATIO = 2.02;
	public static final double LIMELIGHT_ASPECT_RATIO_ERROR = 0.5;

    // ================================================================================
    // IMU Data
    // ================================================================================

    public static final int DRIVEBASE_PIGEON_IMU_ID = 8;
    public static final int DRIVEBASE_PIGEON_IMU_REMOTE_FILTER = 0;

    // ================================================================================
    // Drive Base Motion Magic
    // ================================================================================

    // ================================================================================
    // Drive Base Numbers
    // ================================================================================

    public static final double COUNTS_PER_REVOLUTION = 2048;
    public static final double WHEEL_CIRCUMFERENCE = 0; // TODO: Find Wheel Circumfrence
    public static final int GEAR_RATIO = 0; // TODO: Find Gear Ratio
    public static final double MAX_MOTOR_SPEED = 1;

    // TODO: Set Proper PID Values
    // P, I, D, F, INTERGRAL_ZONE, PEAKOUTPUT, CLOSEDLOOPTIME_MS
    public static final MotionMagic DRIVEBASE_MOTIONMAGIC_DRIVE_GAINS = new MotionMagic(0.2, 0, 0, 0, 100, 1, 1);
    public static final MotionMagic DRIVEBASE_MOTIONMAGIC_TURN_GAINS = new MotionMagic(0.2, 0, 0, 0, 100, 1, 1);
    public static final int DRIVEBASE_MOTIONMAGIC_DRIVE_SLOT = SLOT_0;
    public static final int DRIVEBASE_MOTIONMAGIC_TURN_SLOT = SLOT_1;
    public static final int DRIVEBASE_MOTIONMAGIC_DRIVE_REMOTE = REMOTE_0;
    public static final int DRIVEBASE_MOTIONMAGIC_TURN_REMOTE = REMOTE_1;


    // AUTON

    public static final int ELEVATOR_POSITION_TOLERANCE = 300;
	public static final double DRIVEBASE_ANGLE_TOLERANCE = 5; //TODO: find good angle

    public static final double PIVOT_ERROR_RANGE = 100;
    
    // Sensors

    public static final int[] DIGITAL_INPUT_IDS = {6, 8, 7, 9};
	public static final int PIVOT_PDP_SLOT = 4;
	public static final int FEEDER_PDP_SLOT = 5;
    public static final long FEEDER_PUSH_BALL_DOWN = 300;
    
    // ================================================================================
    // Music
    // ================================================================================
	public static final boolean MUSIC_AUTO_QUEUE = true;
}
