package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.subsystems.*;
import frc.robot.subsystems.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class FloorIntakeCommand extends ParallelCommandGroup {

	public FloorIntakeCommand(Intake intake, Hopper hopper, Pivot pivot, Feeder feeder, Flywheel flywheel) {

		addCommands(
			new IntakeCommand(intake, false),
			new HopperCommand(hopper, feeder, flywheel, false),
			new PivotCommand(pivot, Pivot.POSITION.DOWN)
		);
	}
}