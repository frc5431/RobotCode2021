package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.vision.Limelight;
import frc.robot.commands.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class ShootSuperCommand extends ParallelCommandGroup {
	// public static boolean isAboutToShoot = false;
	// private final SuperStopShoot stop;
	private final Feeder feeder;
	private final Flywheel flywheel;
	private final Hopper hopper;


	public ShootSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase,
			boolean close, boolean rpmWait) {
		// stop = new SuperStopShoot(feeder, intake, hopper, flywheel);
		this.feeder = feeder;
		this.flywheel = flywheel;
		this.hopper = hopper;

		addCommands(
				new SequentialCommandGroup(
					new WaitCommand(0.1), new FlywheelCommand(flywheel, close ? Flywheel.Velocity.HALF : Flywheel.Velocity.FULL
				)
				),
				new SequentialCommandGroup(
					// new ParallelCommandGroup(
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = false;}), // Disable Auto Indexer
					new PushBallDownCommand(feeder),
					// new InstantCommand(() -> {
					// 	// while (flywheel.getTargetVelocity() == 0) {}
					// 	while (){}
					// 	Logger.l("Leaving Flywheel Wait In (ShootSuperCommand.java)");
					// }),
					new WaitTillFlywheelAtSpeed(flywheel, rpmWait),
					new PushBallsUpSubCommand(intake, hopper, feeder, flywheel, close, rpmWait),
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = true;}), // Enable Auto Indexer
					new InstantCommand(() -> {FlywheelCommand.KILL = true;})
				)
		);
	}

	@Override
	public void end(boolean interrupted) {
		feeder.resetVars();
		feeder.set(0);
		flywheel.set(Velocity.OFF);
		hopper.set(0, 0);
		Feeder.ENABLE_AUTO_FEEDER = true;
		FlywheelCommand.KILL = false;
		super.end(interrupted);
		Logger.l("Shooter Super Command Finished!");

	}
}