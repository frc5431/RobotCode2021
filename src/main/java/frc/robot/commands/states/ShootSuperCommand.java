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

	private final Targetor targetor;

	public ShootSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase,
	Limelight limelight, boolean close) {
		this(intake, hopper, feeder, flywheel, drivebase, limelight,close, true);
	}

	public ShootSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase,
			Limelight limelight, boolean close, boolean enableLimelight) {
		// stop = new SuperStopShoot(feeder, intake, hopper, flywheel);
		this.feeder = feeder;
		this.flywheel = flywheel;
		this.hopper = hopper;

		this.targetor = new Targetor(drivebase, limelight);

		addCommands(
				new SequentialCommandGroup(
					new WaitCommand(0.1), new FlywheelCommand(flywheel, close ? Flywheel.Velocity.HALF : Flywheel.Velocity.FULL
				)
				// new PushBallsUpSubCommand(intake, hopper, feeder)
				),
				new SequentialCommandGroup(
					// new ParallelCommandGroup(
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = false;}), // Disable Auto Indexer
					new PushBallDownCommand(feeder),

					new InstantCommand(() -> {while(drivebase.getCurrentCommand() == targetor);}),
					// new InstantCommand(() -> {),

					new PushBallsUpSubCommand(intake, hopper, feeder, flywheel),
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = true;}), // Enable Auto Indexer
					new InstantCommand(() -> {FlywheelCommand.KILL = true;})
				)
		);
		if(enableLimelight){
			addCommands(targetor);
		}
	}

	@Override
	public void end(boolean interrupted) {
		Logger.l("Shooter Super Command Finished!");
		feeder.resetVars();
		feeder.set(0);
		flywheel.set(Velocity.OFF);
		hopper.set(0, 0);
		Feeder.ENABLE_AUTO_FEEDER = true;
		FlywheelCommand.KILL = false;
		super.end(interrupted);
	}
}