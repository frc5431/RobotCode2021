package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.robot.util.ShootPosition;
import frc.team5431.titan.core.misc.Logger;
import frc.robot.Constants;
import frc.robot.Systems;
import frc.robot.commands.*;
import frc.robot.commands.subsystems.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class ShootSuperCommand extends ParallelCommandGroup {
	private final Systems systems;

	public ShootSuperCommand(Systems systems,
			ShootPosition pos, boolean rpmWait) {
		this.systems = systems;

		addCommands(
				new SequentialCommandGroup(
					new WaitCommand(Constants.SHOOTER_FLYWHEEL_COMMAND_WAIT), new FlywheelCommand(systems, (pos == ShootPosition.CLOSE) ? Flywheel.Velocity.HALF : ((pos == ShootPosition.FAR) ? Flywheel.Velocity.FULL : Flywheel.Velocity.AUTON))
				),
				new SequentialCommandGroup(
					new InstantCommand(() -> {Feeder.ENABLE_AUTO_FEEDER = false;}), // Disable Auto Indexer
					new PushBallDownCommand(systems),
					new WaitTillFlywheelAtSpeed(systems, rpmWait),
					new PushBallsUpSubCommand(systems, pos, rpmWait),
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