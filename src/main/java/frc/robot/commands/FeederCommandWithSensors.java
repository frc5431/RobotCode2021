package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Feeder.FeederStateTeleop;
import frc.team5431.titan.core.misc.Logger;

/**
 * This should be used in a sequential as power cells will get stuck on the
 * flywheel so after this command you should move the feeder down so you will
 * not destroy a power cell
 * 
 * @author Ryan Hirasaki
 */
public class FeederCommandWithSensors extends CommandBase {
	private final Feeder feeder;
	private int count;

	boolean ballSeen;

	public FeederCommandWithSensors(Feeder feeder) {
		this.feeder = feeder;

		addRequirements(feeder);
	}

	@Override
	public void initialize() {
		Logger.l("Feeder With Sensors Command Began");
		feeder.set(1);
		count = 0;
		ballSeen = false;
	}

	@Override
	public void execute() {
		feeder.set(feeder.feederLoadAndShoot());
	}

	@Override
	public boolean isFinished() {
		return feeder.getState() == FeederStateTeleop.READY;
	}

	@Override
	public void end(boolean interrupted) {
		Logger.l("Feeder With Sensors Command Finished");
		feeder.set(0);
	}

}