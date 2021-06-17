package frc.robot;

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
import frc.robot.commands.music.*;
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

		printAutonChooser();
    }

	public void printAutonChooser() {
		chooser.setDefaultOption("Drive forward, backward, shoot", AutonStates.DRIVE_FORWARD_BACKWARD_SHOOT);
		chooser.addOption("Drive forward", AutonStates.DRIVE_FORWARD);
		chooser.addOption("Drive forward, backward", AutonStates.DRIVE_FORWARD_BACKWARD);
		SmartDashboard.putData("Auton Select", chooser);
	}

	private void bindKeys() {
		{	// Sample Music Controls
			// Play
			new JoystickButton(buttonBoard, 2).whenPressed(new MusicPlayCommand(systems.getMusic()));
			// Pause
			new JoystickButton(buttonBoard, 16).whenPressed(new MusicPauseCommand(systems.getMusic()));
			// Stop
			new JoystickButton(buttonBoard, 13).whenPressed(new MusicStopCommand(systems.getMusic()));

			// Advance song by 1
			new JoystickButton(buttonBoard, 3).whenPressed(MusicLoadCommand.NEXT_SONG(systems.getMusic()));
			// Decrement song by 1
			new JoystickButton(buttonBoard, 7).whenPressed(MusicLoadCommand.PREVIOUS_SONG(systems.getMusic()));
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
		if (systems.getMusic().isPlaying())
			systems.getMusic().stop();
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

	public void testInit() {
		// ===========================
		// ||                       ||
		// ||     Music Controls    ||
		// ||                       ||
		// ===========================
		{	// Sample Music Controls
			// // Play
			// new JoystickButton(buttonBoard, 2).whenPressed(new MusicPlayCommand(systems.getMusic()));
			// // Pause
			// new JoystickButton(buttonBoard, 16).whenPressed(new MusicPauseCommand(systems.getMusic()));
			// // Stop
			// new JoystickButton(buttonBoard, 13).whenPressed(new MusicStopCommand(systems.getMusic()));

			// // Advance song by 1
			// new JoystickButton(buttonBoard, 3).whenPressed(MusicLoadCommand.NEXT_SONG(systems.getMusic()));
			// // Decrement song by 1
			// new JoystickButton(buttonBoard, 7).whenPressed(MusicLoadCommand.PREVIOUS_SONG(systems.getMusic()));
		}
	}

	public void testPeriodic() {

	}
}