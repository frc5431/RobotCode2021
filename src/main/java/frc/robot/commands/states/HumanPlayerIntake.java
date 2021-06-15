package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.subsystems.*;
import frc.robot.Systems;

/**
 * @author Rishmita Rao
 * @author Ryan Hirasaki
 */
public class HumanPlayerIntake extends ParallelCommandGroup {
	private final Systems systems;
	public HumanPlayerIntake(Systems systems) {
		this.systems = systems;

		addCommands(
			new HopperCommand(systems, false)
		);
	}

	@Override
	public boolean isFinished() {
		return systems.getFeeder().getBallCount() >= 3;
	}
}