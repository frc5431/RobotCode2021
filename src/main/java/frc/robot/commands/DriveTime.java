package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

public class DriveTime extends CommandBase {
	private final Drivebase drivebase;
	private final long driveTimeout;
	private final double power;
	private long startTime;

	public DriveTime(Drivebase drivebase, double power, long driveTimeout) {
		this.drivebase = drivebase;
		this.driveTimeout = driveTimeout;
		this.power = power;
	}

	@Override
	public void initialize() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		drivebase.drivePercentageArcade(power, 0);
	}

	@Override
	public void end(boolean interrupted) {
		drivebase.drivePercentageArcade(0, 0);
	}

	@Override
	public boolean isFinished() {
		long currTime = System.currentTimeMillis();
		return currTime >= startTime + driveTimeout;
	}
}