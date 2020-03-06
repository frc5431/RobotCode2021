package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.subsystems.*;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Pivot;

/**
 * @author Rishmita Rao
 * @author Ryan Hirasaki
 */
public class HumanPlayerIntake extends ParallelCommandGroup {
	private final Feeder feeder;
	public HumanPlayerIntake(Feeder feeder, Hopper hopper, Pivot pivot, Flywheel flywheel) {
		this.feeder = feeder;

		addCommands(
			new HopperCommand(hopper, feeder, flywheel, false),
			new PivotCommand(pivot, Pivot.POSITION.DOWN)
		);
	}

	@Override
	public boolean isFinished() {
		return feeder.getBallCount() >= 3;
	}
}