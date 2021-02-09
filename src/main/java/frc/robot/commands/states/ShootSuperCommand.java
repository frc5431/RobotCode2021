package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.team5431.titan.core.misc.Logger;
import frc.robot.Systems;
import frc.robot.commands.*;
import frc.robot.commands.subsystems.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class ShootSuperCommand extends ParallelCommandGroup {
	// public static boolean isAboutToShoot = false;
	// private final SuperStopShoot stop;
	private final Systems systems;


	public ShootSuperCommand(Systems systems,
			boolean close, boolean rpmWait) {
		// stop = new SuperStopShoot(feeder, intake, hopper, flywheel);
		this.systems = systems;

		addCommands(
				new SequentialCommandGroup(
					new WaitCommand(0.1), new FlywheelCommand(systems, close ? Flywheel.Velocity.HALF : Flywheel.Velocity.FULL
				)
				),
				new SequentialCommandGroup(
					// new ParallelCommandGroup(
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = false;}), // Disable Auto Indexer
					new PushBallDownCommand(systems),
					// new InstantCommand(() -> {
					// 	// while (flywheel.getTargetVelocity() == 0) {}
					// 	while (){}
					// 	Logger.l("Leaving Flywheel Wait In (ShootSuperCommand.java)");
					// }),
					new WaitTillFlywheelAtSpeed(systems, rpmWait),
					new PushBallsUpSubCommand(systems, close, rpmWait),
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = true;}), // Enable Auto Indexer
					new InstantCommand(() -> {FlywheelCommand.KILL = true;})
				)
		);
	}

	@Override
	public void end(boolean interrupted) {
		systems.getFeeder().resetVars();
		systems.getFeeder().set(0);
		systems.getFlywheel().set(Velocity.OFF);
		systems.getHopper().set(0, 0);
		Feeder.ENABLE_AUTO_FEEDER = true;
		FlywheelCommand.KILL = false;
		super.end(interrupted);
		Logger.l("Shooter Super Command Finished!");

	}
}