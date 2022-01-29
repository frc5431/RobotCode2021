package frc.robot;

import java.net.URI;
import java.net.URISyntaxException;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.auton.AutonStates;
import frc.robot.auton.DriveFBShoot;
import frc.robot.auton.DriveForward;
import frc.robot.auton.DriveForwardBackward;
import frc.robot.commands.*;
import frc.robot.commands.defaults.*;
import frc.robot.commands.states.*;
import frc.robot.commands.subsystems.*;
import frc.robot.subsystems.*;
import frc.robot.util.ShootPosition;
import frc.robot.util.WebsocketButtonPad;
import frc.team5431.titan.core.joysticks.*;
import frc.team5431.titan.core.joysticks.LogitechExtreme3D.Axis;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.vision.*;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 * @author Rishmita Rao
 * @author Daniel Brubaker
 */
public class RobotMap {
	private final Systems systems = new Systems();

	private final Xbox driver = new Xbox(0);
	private final Joystick buttonBoard = new Joystick(1);
	private final LogitechExtreme3D operator = new LogitechExtreme3D(2);

	protected WebsocketButtonPad launchpad;

	private final Limelight limelight = new Limelight(Constants.VISION_FRONT_LIMELIGHT);

	SendableChooser<AutonStates> chooser = new SendableChooser<>();

	public RobotMap() {
		try {
			launchpad = new WebsocketButtonPad(new URI(
					"ws://10.54.31.507:5802"
			));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		setupLaunchpad();

		limelight.setLEDState(LEDState.DEFAULT);
		limelight.setPipeline(9);
		bindKeys();

		printAutonChooser();
    }

	public void printAutonChooser() {
		chooser.setDefaultOption("Drive forward, backward, shoot", AutonStates.DRIVE_FORWARD_BACKWARD_SHOOT);
		chooser.addOption("Drive forward", AutonStates.DRIVE_FORWARD);
		chooser.addOption("Drive forward, backward", AutonStates.DRIVE_FORWARD_BACKWARD);
		SmartDashboard.putData("Auton Select", chooser);
	}

	private void bindKeys() {
		
		// ===========================
		// ||                       ||
		// ||    XBOX Controller    ||
		// ||                       ||
		// ===========================
		{
			// Targetor REMINDER: SET TO FAR FOR TEST
			new JoystickButton(driver, Xbox.Button.B.ordinal() + 1)
					.whenHeld(new Targetor(systems, limelight));

			// Calibrate the Limelight
			new JoystickButton(driver, Xbox.Button.X.ordinal() + 1);

			// Intake
			new JoystickButton(driver, Xbox.Button.A.ordinal() + 1).whileHeld(new IntakeCommand(systems, 1));

			// Balancer Left
			new JoystickButton(driver, Xbox.Button.BUMPER_L.ordinal() + 1)
					.whenHeld(new BalancerCommand(systems, true));

			// Balancer Right
			new JoystickButton(driver, Xbox.Button.BUMPER_R.ordinal() + 1)
					.whenHeld(new BalancerCommand(systems, false));

		}

		// ===========================
		// ||                       ||
		// ||     Button  Board     ||
		// ||                       ||
		// ===========================
		{
			// Human Player Intake (Feeder Intake)
			new JoystickButton(buttonBoard, 11).toggleWhenPressed(new HumanPlayerIntake(systems));

			// Floor Intake
			new JoystickButton(buttonBoard, 4).toggleWhenPressed(new FloorIntakeCommand(systems));


			// Pivot Down
			new JoystickButton(buttonBoard, 6).whenPressed(new PivotCommand(systems, Pivot.POSITION.DOWN));

			// Pivot Up
			new JoystickButton(buttonBoard, 5).whenPressed(new PivotCommand(systems, Pivot.POSITION.UP));

			// Vision Far
			// new JoystickButton(buttonBoard, 3).whenHeld(new Targetor(drivebase, limelight));

			// Vision Close
			new JoystickButton(buttonBoard, 7).whenPressed(new Targetor(systems, limelight));

			// Shoot Close
			new JoystickButton(buttonBoard, 1)
					.whenHeld(new ShootSuperCommand(systems, ShootPosition.CLOSE, false));

			// Shoot Far
			new JoystickButton(buttonBoard, 14)
					.whenHeld(new ShootSuperCommand(systems, ShootPosition.FAR, false));

			// Intake (By itself)
			// new JoystickButton(buttonBoard, 2)
			// 		.toggleWhenPressed(new IntakeCommand(intake, Constants.INTAKE_DEFAULT_SPEED));

			/*
			 * Not used as it is bound to triggers for the driver but here for historical
			 * purposes
			 * 
			 * // Climb new JoystickButton(buttonBoard, 8);
			 */

			// Stuck On Side.
			// Run Hopper and pivot up with intake on
			// new JoystickButton(buttonBoard, 13);

			// Stow
			new JoystickButton(buttonBoard, 12)
					.whenPressed(new StowSuperCommand(systems));

			// Auto Switch
			// new JoystickButton(buttonBoard, 9).whenPressed(new KillBallCounter(feeder));
		}

		// ===========================
		// ||                       ||
		// ||   Logitech Operator   ||
		// ||                       ||
		// ===========================
		{
			// Indexer Up
			// new POVButton(operator, 0)
			this.launchpad.getButtonInstance(1, 1)
					.whenPressed(new FeederCommand(systems, -Constants.SHOOTER_FEEDER_DEFAULT_SPEED, false))
					.whenReleased(new FeederCommand(systems, 0, false));

			// Indexer Down
			// new POVButton(operator, 180)
			this.launchpad.getButtonInstance(2, 1)
					.whenPressed(new FeederCommand(systems, Constants.SHOOTER_FEEDER_DEFAULT_SPEED, false))
					.whenReleased(new FeederCommand(systems, 0, false));

			// Trigger Flywheel (Shoot Far)
			new JoystickButton(operator, LogitechExtreme3D.Button.TRIGGER.ordinal() + 1)
					.whenHeld(new FlywheelTriggerCommand(systems, Constants.FLYHWEEL_MAX_VELOCITY, () -> -operator.getRawAxis(Axis.SLIDER)))
					.whenReleased(new FlywheelCommand(systems, Flywheel.Velocity.OFF));

			// Arbitrary Flywheel control (Shoot Close)
			new JoystickButton(operator, LogitechExtreme3D.Button.TWELVE.ordinal() + 1)
					.whenHeld(new FlywheelCommand(systems, Flywheel.Velocity.HALF))
					.whenReleased(new FlywheelCommand(systems, Flywheel.Velocity.OFF));

			// Three Hopper Out
			new JoystickButton(operator, LogitechExtreme3D.Button.THREE.ordinal() + 1)
					.whenHeld(new HopperCommand(systems, Constants.HOPPER_LEFT_SPEED, Constants.HOPPER_RIGHT_SPEED));

			// Five Hopper In
			new JoystickButton(operator, LogitechExtreme3D.Button.FIVE.ordinal() + 1)
					.whileHeld(new HopperCommand(systems, -Constants.HOPPER_LEFT_SPEED, -Constants.HOPPER_RIGHT_SPEED));

			// Six Intake Pivot Down
			new JoystickButton(operator, LogitechExtreme3D.Button.SEVEN.ordinal() + 1)
					.whenPressed(new PivotCommand(systems, Pivot.POSITION.DOWN));

			// Four Intake Pivot Up
			new JoystickButton(operator, LogitechExtreme3D.Button.EIGHT.ordinal() + 1)
					.whenPressed(new PivotCommand(systems, Pivot.POSITION.UP));

			// Two Intake
			new JoystickButton(operator, LogitechExtreme3D.Button.TWO.ordinal() + 1)
					.whenHeld(new IntakeCommand(systems, 1));

			// Vision Far 11
			new JoystickButton(operator, 11).whenHeld(new Targetor(systems, limelight));

			// Vision Close 12
			new JoystickButton(operator, 12).whenHeld(new Targetor(systems, limelight));

			// Ten Intake
			new JoystickButton(operator, 10).whenHeld(new IntakeCommand(systems, Constants.INTAKE_DEFAULT_SPEED, false));

			// Nine Intake Reverse
			new JoystickButton(operator, 9).whenHeld(new IntakeCommand(systems, Constants.INTAKE_DEFAULT_SPEED, true));

			// Flywheel close 6
			new JoystickButton(operator, 6).whenHeld(new FlywheelCommand(systems, Flywheel.Velocity.HALF));
		}

		// ===========================
		// ||                       ||
		// ||   Default  Commands   ||
		// ||                       ||
		// ===========================
		{
			driver.setDeadzone(Constants.DRIVER_XBOX_DEADZONE);

			systems.getDrivebase().setDefaultCommand(new DefaultDrive(systems,
					() -> -driver.getRawAxis(Xbox.Axis.LEFT_Y)*0.5,() -> -driver.getRawAxis(Xbox.Axis.LEFT_X)*0.5));

			systems.getElevator().setDefaultCommand(new DefaultElevator(systems,
					() -> driver.getRawAxis(Xbox.Axis.TRIGGER_RIGHT) - driver.getRawAxis(Xbox.Axis.TRIGGER_LEFT)));
		}
	}

	public void setupLaunchpad() {
		try {
			launchpad.connect();
		} catch (Exception e) {
			System.out.println("Websocket failure: ");
			e.printStackTrace();
		}
	}

	public void connectLaunchpad() {
		if (launchpad == null) {
			throw new RuntimeException("Launchpad is null, cannot connect!");
		}
		if(!launchpad.isOpen()){
			try {
				launchpad.connect();
			} catch (Exception e) {
				System.out.println("Websocket connection failure");
			}
		}
		else {
			System.out.println("Websocket connected");
		}
	}

	public void resetBallCount() {
		systems.getFeeder().resetVars();
	}

	public CommandBase getAutonomousCommand() {
		switch(chooser.getSelected()) {
			case DRIVE_FORWARD:
				return new DriveForward(systems);
			case DRIVE_FORWARD_BACKWARD:
				return new DriveForwardBackward(systems);
			case DRIVE_FORWARD_BACKWARD_SHOOT:
				return new DriveFBShoot(systems, limelight);
			default:
				return new SequentialCommandGroup(
					
				);
		}
	}

	public void resetEncoders() {
		systems.getPivot().reset();
	}

	public void enabled() {
		systems.getPivot().setNeutralMode(Constants.PIVOT_DRIVEMODE);
		Logger.l("enabled!");
	}

	public void disabled() {
		// These two functions should do the same thing but is both here just in case
		CommandScheduler.getInstance().cancelAll();
		systems.clearAllCommands();
		
		systems.getPivot().setNeutralMode(Constants.PIVOT_NEUTRALMODE);

		// Lets Not Blind the Refs :)
		limelight.setPipeline(Constants.LIMELIGHT_PIPELINE_OFF);

		// Make sure chooser is always shown
		printAutonChooser();

		Logger.l("disabled!");
	}

	public void disabledPeriodic() {
		resetEncoders();
		printAutonChooser();
	}
}