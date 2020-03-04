package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Speeds;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.robot.subsystems.LimelightSubsystem.Positions;
import frc.team5431.titan.core.vision.Limelight;
import frc.robot.commands.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class ShootSuperCommandClose extends ParallelCommandGroup {
	public static boolean isAboutToShoot = false;
	// private final SuperStopShoot stop;
	private final Feeder feeder;
	private final Flywheel flywheel;
	private final Hopper hopper;

	public ShootSuperCommandClose(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase,
			Limelight limelight) {
		// stop = new SuperStopShoot(feeder, intake, hopper, flywheel);
		this.feeder = feeder;
		this.flywheel = flywheel;
		this.hopper = hopper;

		addCommands(
				new SequentialCommandGroup(
					new WaitCommand(0.1), new FlywheelCommand(flywheel, Flywheel.Velocity.HALF
				)
				// new PushBallsUpSubCommand(intake, hopper, feeder)
				),
				new SequentialCommandGroup(
					// new ParallelCommandGroup(
					new PushBallDownCommand(feeder),
					// put targetting command here too
					// ),
					new PushBallsUpSubCommand(intake, hopper, feeder, flywheel)
				)
		// Bring up to speed
		// new FlywheelCommand(flywheel, Velocity.HALF), //Waits till up to speed
		// Push Balls. Keep running until current command is interuppted
		);

		// andThen(
		// new SuperStopShoot(feeder, intake, hopper, flywheel)
		// );
	}

	@Override
	public void end(boolean interrupted) {
		feeder.resetVars();
		feeder.set(0);
		flywheel.set(Velocity.OFF);
		hopper.set(0, 0);

	}
}