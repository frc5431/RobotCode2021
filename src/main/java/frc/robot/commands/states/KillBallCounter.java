package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class KillBallCounter extends CommandBase {
	private final Feeder feeder;

	public KillBallCounter(Feeder feeder) {
		this.feeder = feeder;

		addRequirements(feeder);
	}

	@Override
	public void initialize() {
		feeder.resetVars();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}