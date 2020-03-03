package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.FeederCommand;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.PivotCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Pivot;

public class HumanPlayerIntake extends ParallelCommandGroup {
	private final Feeder feeder;
	public HumanPlayerIntake(Feeder feeder, Hopper hopper, Pivot pivot) {
		this.feeder = feeder;

		addCommands(
			new HopperCommand(hopper, false),
			new PivotCommand(pivot, Pivot.POSITION.DOWN)
		);
	}

	@Override
	public boolean isFinished() {
		return feeder.getBallCount() >= 3;
	}
}