package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.auton.AutonStates;
import frc.robot.auton.ShootCloseAndDrive;
import frc.robot.commands.*;
import frc.robot.commands.states.*;
import frc.robot.commands.subsystems.*;
import frc.robot.subsystems.*;
import frc.robot.util.ShootPosition;
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

	private final Limelight limelight = new Limelight(Constants.VISION_FRONT_LIMELIGHT);

	SendableChooser<AutonStates> chooser = new SendableChooser<>();

	public RobotMap() {
		limelight.setLEDState(LEDState.DEFAULT);
		limelight.setPipeline(9);
		bindKeys();
		outData();
    }

	public void outData() {
		chooser.setDefaultOption("Shoot, drive backward, drive forward, stop", AutonStates.SHOOT_AND_DRIVE_BACK_AND_FORWARD);
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
			// Human Player Intake
			new JoystickButton(buttonBoard, 11).toggleWhenPressed(new HumanPlayerIntake(systems));

			// Floor Intake
			new JoystickButton(buttonBoard, 4).toggleWhenPressed(new FloorIntakeCommand(systems).andThen(new PivotCommand(systems, Pivot.POSITION.UP))); // TODO: test andThen


			// Pivot Down
			new JoystickButton(buttonBoard, 6).whenPressed(new PivotCommand(systems, Pivot.POSITION.DOWN));

			// Pivot Up
			new JoystickButton(buttonBoard, 5).whenPressed(new PivotCommand(systems, Pivot.POSITION.UP));

			// Vision Far
			// new JoystickButton(buttonBoard, 3).whenHeld(new Targetor(drivebase, limelight));

			// Vision Close
			new JoystickButton(buttonBoard, 7).whenPressed(new Targetor(systems, limelight));

			// // Human player Intake Super Command (labeled "in") TODO
			// new JoystickButton(buttonBoard, 11)
			// .whenPressed(new FloorIntakeSuperCommand(intake, hopper, flywheel, pivot,
			// feeder));

			// Shoot Close
			new JoystickButton(buttonBoard, 1)
					.whenHeld(new ShootSuperCommand(systems, ShootPosition.CLOSE, false));

			// // Shoot Far
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
			new POVButton(operator, 0).whenPressed(new FeederCommand(systems, -Constants.SHOOTER_FEEDER_DEFAULT_SPEED, false))
					.whenReleased(new FeederCommand(systems, 0, false));

			// Indexer Down
			new POVButton(operator, 180).whenPressed(new FeederCommand(systems, Constants.SHOOTER_FEEDER_DEFAULT_SPEED, false))
					.whenReleased(new FeederCommand(systems, 0, false));

			// Trigger Flywheel (Shoot Far)
			new JoystickButton(operator, LogitechExtreme3D.Button.TRIGGER.ordinal() + 1)
					.whenHeld(new FlywheelTriggerCommand(systems, Flywheel.Velocity.FULL, () -> -operator.getRawAxis(Axis.SLIDER)))
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
			// .whenReleased(new PivotCommand(pivot, SPEED.ZERO));

			// Four Intake Pivot Up
			new JoystickButton(operator, LogitechExtreme3D.Button.EIGHT.ordinal() + 1)
					.whenPressed(new PivotCommand(systems, Pivot.POSITION.UP));
			// .whenReleased(new PivotCommand(pivot, SPEED.ZERO));

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
					() -> -driver.getRawAxis(Xbox.Axis.LEFT_Y),() -> -driver.getRawAxis(Xbox.Axis.LEFT_X)));

			systems.getElevator().setDefaultCommand(new DefaultElevator(systems,
					() -> driver.getRawAxis(Xbox.Axis.TRIGGER_RIGHT) - driver.getRawAxis(Xbox.Axis.TRIGGER_LEFT)));
			
			// systems.getFlywheel()
			// 		.setDefaultCommand(new DefaultFlywheel(systems, () -> -operator.getRawAxis(Axis.SLIDER)));
		}
	}

	public void resetBallCount() {
		systems.getFeeder().resetVars();
	}

	public CommandBase getAutonomousCommand() {
		switch(chooser.getSelected()) {
		case SHOOT_AND_DRIVE_BACK_AND_FORWARD:
			return new ShootCloseAndDrive(systems, limelight);
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
		
		resetEncoders();
		systems.getPivot().setNeutralMode(Constants.PIVOT_NEUTRALMODE);

		// Lets Not Blind the Refs :)
		limelight.setPipeline(Constants.LIMELIGHT_PIPELINE_OFF);

		Logger.l("disabled!");
	}
}