package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Systems;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.PivotCommand;
import frc.robot.subsystems.Pivot;

public class HumanPlayerIntake extends ParallelCommandGroup {
	private final Systems systems;
	public HumanPlayerIntake(Systems systems) {
		this.systems = systems;

		addCommands(
			new HopperCommand(systems, false),
			new PivotCommand(systems, Pivot.POSITION.DOWN)
		);
	}

	@Override
	public boolean isFinished() {
		return systems.getFeeder().getBallCount() >= 3;
	}
}