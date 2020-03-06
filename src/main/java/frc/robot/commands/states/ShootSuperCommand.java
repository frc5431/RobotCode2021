package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.team5431.titan.core.misc.Logger;
import frc.robot.Constants;
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
	private final Feeder feeder;
	private final Flywheel flywheel;
	private final Hopper hopper;
	private final boolean rpmWait;
	private long startTime;

	public ShootSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase,
			boolean close, boolean rpmWait) {
		// stop = new SuperStopShoot(feeder, intake, hopper, flywheel);
		this.feeder = feeder;
		this.flywheel = flywheel;
		this.hopper = hopper;
		this.rpmWait = rpmWait;

		addCommands(
				new SequentialCommandGroup(
					new WaitCommand(0.1), new FlywheelCommand(flywheel, close ? Flywheel.Velocity.HALF : Flywheel.Velocity.FULL
				)
				),
				new SequentialCommandGroup(
					// new ParallelCommandGroup(
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = false;}), // Disable Auto Indexer
					new FunctionalCommand(
						() -> startTime = System.currentTimeMillis(),
						() -> {
							feeder.set(-0.4);
							Logger.l("Pushing balls down");
						}, 
						(interrupted) -> {
							feeder.set(0);
							Logger.l("Finished pushing balls down");
						}, 
						() -> startTime + Constants.FEEDER_PUSH_BALL_DOWN <= System.currentTimeMillis() || 
								feeder.getValueOfDIOSensor(3), 
						feeder
					),
					// new InstantCommand(() -> {
					// 	// while (flywheel.getTargetVelocity() == 0) {}
					// 	while (){}
					// 	Logger.l("Leaving Flywheel Wait In (ShootSuperCommand.java)");
					// }),
					new WaitUntilCommand(this::isFlywheelAtSpeed),
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

	private boolean isFlywheelAtSpeed() {
		return !(!rpmWait && (!flywheel.atVelocity() || flywheel.getTargetVelocity() == 0));
	}
}