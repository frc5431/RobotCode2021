package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Drivebase;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Rishmita Rao
 * @author Ryan Hirasaki
 */
public class DriveTime extends CommandBase {
	private final Drivebase drivebase;
	private final double driveTimeout;
	private final double power;
	private long startTime;

	public DriveTime(Systems systems, double power, double driveTimeout) {
		this.drivebase = systems.getDrivebase();
		this.driveTimeout = driveTimeout;
		this.power = power;

		addRequirements(drivebase);
	}

	@Override
	public void initialize() {
		startTime = System.currentTimeMillis();
		Logger.l("starting drive time");

	}

	@Override
	public void execute() {
		drivebase.drivePercentageArcade(power, 0);
	}

	@Override
	public void end(boolean interrupted) {
		drivebase.drivePercentageArcade(0, 0);
		Logger.l("finishing drive time");
	}

	@Override
	public boolean isFinished() {
		long currTime = System.currentTimeMillis();
		return currTime >= startTime + driveTimeout;
	}
}