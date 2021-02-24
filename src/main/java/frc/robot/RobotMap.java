package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.auton.AutonStates;
import frc.robot.commands.*;
import frc.robot.commands.states.*;
import frc.robot.commands.subsystems.*;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.joysticks.*;
import frc.team5431.titan.core.joysticks.utils.CompassPOV;
import frc.team5431.titan.core.vision.*;
import frc.team5431.titan.core.robot.POVButton;
import frc.team5431.titan.core.robot.JoystickButton;
import frc.team5431.titan.pathfinder.PathLoader;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 * @author Rishmita Rao
 * @author Daniel Brubaker
 */
public class RobotMap {
	private final Systems systems = new Systems();

    // private final Music music;

	private final Xbox driver = new Xbox(0);
	private final Joystick buttonBoard = new Joystick(1);
	private final LogitechExtreme3D operator = new LogitechExtreme3D(2);

    private final Limelight limelight = new Limelight(Constants.VISION_FRONT_LIMELIGHT);
    private final PathLoader pathweaver = new PathLoader(Constants.DRIVEBASE_PATHWEAVER_CONFIG, Constants.DRIVEBASE_PATHWEAVER_PATH);

	SendableChooser<AutonStates> chooser = new SendableChooser<>();

	public RobotMap() {
		limelight.setLEDState(LEDState.DEFAULT);
		limelight.setPipeline(9);
		bindKeys();
		outData();

		// chooser.setDefaultOption("Shoot, Drive foward one, stop", StartPosition.SHOOT_AND_DRIVE_FOWARD_ONE);
		// chooser.addOption("Drive back 0.5, Drive Foward 0.5, Shoot, Drive foward one, stop", StartPosition.DRIVE_BACK_AND_FOWARD_THEN_SHOOT_THEN_DRIVE_ONE);
		// SmartDashboard.putData("Auton Select", chooser);

        // music = new Music(systems.getAllFalcons());
        // music.setAutoQueue(Constants.MUSIC_AUTO_QUEUE);
    }

    public void outData() {
        chooser.setDefaultOption("Shoot, Drive foward one, stop", 
                AutonStates.SHOOT_AND_DRIVE_FORWARD_ONE);
        chooser.addOption("Drive back 0.5, Drive Foward 0.5, Shoot, Drive foward one, stop",
                AutonStates.DRIVE_BACK_AND_FORWARD_THEN_SHOOT_THEN_DRIVE_ONE);
        SmartDashboard.putData("Auton Select", chooser);
        // SmartDashboard.putData("Auton Select", chooser);
    }

    private void bindKeys() {

        // ===========================
        // ===   XBOX Controller   ===
        // ===========================
        {
            // Targetor REMINDER: SET TO FAR FOR TEST
            new JoystickButton(driver, Xbox.Button.B)
                .whenHeld(new Targetor(systems, limelight));

            // Calibrate the Limelight
            new JoystickButton(driver, Xbox.Button.X);

            // Intake
            new JoystickButton(driver, Xbox.Button.A)
                .whileHeld(new IntakeCommand(systems, 1));

            // Balancer Left
            new JoystickButton(driver, Xbox.Button.BUMPER_L)
                .whenHeld(new BalancerCommand(systems, true));

            // Balancer Right
            new JoystickButton(driver, Xbox.Button.BUMPER_R)
                .whenHeld(new BalancerCommand(systems, false));

        }

        // ===========================
        // ===    Button Board     ===
        // ===========================
        {
            // Human Player Intake
            new JoystickButton(buttonBoard, 11)
                .toggleWhenPressed(new HumanPlayerIntake(systems));

            // Floor Intake
            new JoystickButton(buttonBoard, 4)
                .toggleWhenPressed(
                    new FloorIntakeCommand(systems)
                        .andThen(new PivotCommand(systems, Pivot.POSITION.UP))); // TODO: test andThen

            // Pivot Down
            new JoystickButton(buttonBoard, 6)
                .whenPressed(new PivotCommand(systems, Pivot.POSITION.DOWN));

            // Pivot Up
            new JoystickButton(buttonBoard, 5)
                .whenPressed(new PivotCommand(systems, Pivot.POSITION.UP));

            // Vision Far
            // new JoystickButton(buttonBoard, 3)
            //     .whenHeld(new Targetor(drivebase, limelight));

            // Vision Close
            new JoystickButton(buttonBoard, 7)
                .whenPressed(new Targetor(systems, limelight));

            // Human player Intake Super Command (labeled "in") TODO
            // new JoystickButton(buttonBoard, 11)
            //     .whenPressed(new FloorIntakeSuperCommand(intake, hopper, flywheel, pivot, feeder));

            // Shoot Close
            new JoystickButton(buttonBoard, 1)
                .whenHeld(new ShootSuperCommand(systems, true, false));

            // // Shoot Far
            new JoystickButton(buttonBoard, 14)
                .whenHeld(new ShootSuperCommand(systems, false, false));

            // Intake (By itself)
            // new JoystickButton(buttonBoard, 2)
            //     .toggleWhenPressed(new IntakeCommand(intake, Constants.INTAKE_DEFAULT_SPEED));

            // Not used as it is bound to triggers for the driver but here for historical purposes
            // Climb 
            // new JoystickButton(buttonBoard, 8);
            
            // Stuck On Side.
            // Run Hopper and pivot up with intake on
            // new JoystickButton(buttonBoard, 13);

            new JoystickButton(buttonBoard, 12)
                .whenPressed(new StowSuperCommand(systems));

            // Auto Switch
            // new JoystickButton(buttonBoard, 9)
            //     .whenPressed(new KillBallCounter(feeder));
        }

        // ===========================
        // ===  Logitech Operator  ===
        // ===========================
        {
            // Indexer Up
            new POVButton(operator, CompassPOV.NORTH)
                .whenPressed(new FeederCommand(systems, -Constants.SHOOTER_FEEDER_DEFAULT_SPEED, false))
                .whenReleased(new FeederCommand(systems, 0, false));

            // Indexer Down
            new POVButton(operator, CompassPOV.SOUTH)
                .whenPressed(new FeederCommand(systems, Constants.SHOOTER_FEEDER_DEFAULT_SPEED, false))
                .whenReleased(new FeederCommand(systems, 0, false));

            // Trigger Flywheel (Shoot Far)
            new JoystickButton(operator, LogitechExtreme3D.Button.TRIGGER)
                .whenHeld(new FlywheelCommand(systems, Flywheel.Velocity.FULL))
                .whenReleased(new FlywheelCommand(systems, Flywheel.Velocity.OFF));

            // Arbitrary Flywheel control (Shoot Close)
            new JoystickButton(operator, LogitechExtreme3D.Button.TWELVE)
                .whenHeld(new FlywheelCommand(systems, Flywheel.Velocity.HALF))
                .whenReleased(new FlywheelCommand(systems, Flywheel.Velocity.OFF));

            // Three Hopper Out
            new JoystickButton(operator, LogitechExtreme3D.Button.THREE)
                .whenHeld(new HopperCommand(systems, Constants.HOPPER_LEFT_SPEED, Constants.HOPPER_RIGHT_SPEED));

            // Five Hopper In
            new JoystickButton(operator, LogitechExtreme3D.Button.FIVE)
                .whileHeld(new HopperCommand(systems, -Constants.HOPPER_LEFT_SPEED, -Constants.HOPPER_RIGHT_SPEED));

            // Six Intake Pivot Down
            new JoystickButton(operator, LogitechExtreme3D.Button.SEVEN)
                .whenPressed(new PivotCommand(systems, Pivot.POSITION.DOWN));
            //     .whenReleased(new PivotCommand(pivot, SPEED.ZERO));

            // Four Intake Pivot Up
            new JoystickButton(operator, LogitechExtreme3D.Button.EIGHT)
                .whenPressed(new PivotCommand(systems, Pivot.POSITION.UP));
            //     .whenReleased(new PivotCommand(pivot, SPEED.ZERO));

            // Two Intake
            new JoystickButton(operator, LogitechExtreme3D.Button.TWO)
                .whenHeld(new IntakeCommand(systems, 1));

            // Vision Far 11
            new JoystickButton(operator, 11)
                .whenHeld(new Targetor(systems, limelight));

            // Vision Close 12
            new JoystickButton(operator, 12)
                .whenHeld(new Targetor(systems, limelight));

            // Ten Intake
            new JoystickButton(operator, 10)
                .whenHeld(new IntakeCommand(systems, Constants.INTAKE_DEFAULT_SPEED, false));

            // Nine Intake Reverse
            new JoystickButton(operator, 9)
                .whenHeld(new IntakeCommand(systems, Constants.INTAKE_DEFAULT_SPEED, true));

            // Flywheel close 6
            new JoystickButton(operator, 6)
                .whenHeld(new FlywheelCommand(systems, Flywheel.Velocity.HALF));
        }

        // ===========================
        // ===  Default Commands   ===
        // ===========================
        {
            driver.setDeadzone(Constants.DRIVER_XBOX_DEADZONE);

            systems.getDrivebase().setDefaultCommand(new DefaultDrive(systems,
                    () -> -driver.getRawAxis(Xbox.Axis.LEFT_Y), () -> driver.getRawAxis(Xbox.Axis.LEFT_X)));
            // systems.getDrivebase().setDefaultCommand(new DefaultDriveTank(systems,
                    // () -> -driver.getRawAxis(1), () -> -driver.getRawAxis(4)));

            systems.getElevator().setDefaultCommand(new DefaultElevator(systems,
                    () -> driver.getRawAxis(Xbox.Axis.TRIGGER_RIGHT) - driver.getRawAxis(Xbox.Axis.TRIGGER_LEFT)));
        }

        // ===========================
        // ===   Music Controls    ===
        // ===========================
        /*{ 
            // Sample Music Controls 
            // Play 
            new JoystickButton(buttonBoard, 10)
                .whenPressed(new MusicPlayCommand(music)); 
            // Pause 
            new JoystickButton(buttonBoard, 11)
                .whenPressed(new MusicPauseCommand(music)); 
            // Stop 
            new JoystickButton(buttonBoard, 12)
                .whenPressed(new MusicStopCommand(music));
        
            // Advance song by 1 
            new JoystickButton(buttonBoard, 13)
                .whenPressed(MusicLoadCommand.createMusicLoadCommand(music, LoadType.OFFSET, +1)); 
            // Decrement song by 1 
            new JoystickButton(buttonBoard, 14)
                .whenPressed(MusicLoadCommand.createMusicLoadCommand(music, LoadType.OFFSET, -1)); 
        }*/
    }

    public void resetBallCount() {
        systems.getFeeder().resetVars();
    }

    public Command getAutonomousCommand() {
        // switch (chooser.getSelected()) {
        //     case SHOOT_AND_DRIVE_FORWARD_ONE:
        //         return new ShootCloseAndDrive(systems, limelight);
        //     default:
        //     case DRIVE_BACK_AND_FORWARD_THEN_SHOOT_THEN_DRIVE_ONE:
        //         return new SequentialCommandGroup(

        //         );
        // }
        // TODO: implement loading of different paths
        return pathweaver.generateCommand(systems.getDrivebase());
    }

    public void resetEncoders() {
        systems.getPivot().reset();
    }

    public void disabled() {
        // These two functions should do the same thing but is both here just in case
        CommandScheduler.getInstance().cancelAll();
        // resetEncoders();
        // music.stop();
        systems.clearAllCommands();
        resetEncoders();

        // Lets Not Blind the Refs :)
        limelight.setPipeline(Constants.LIMELIGHT_PIPELINE_OFF);
    }
}