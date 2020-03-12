package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Systems;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.PivotCommand;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Pivot.POSITION;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class FloorIntakeCommand extends ParallelCommandGroup {

	private final Systems systems;
	public FloorIntakeCommand(Systems systems) {

		this.systems = systems;
		addCommands(
			new IntakeCommand(systems, false),
			new HopperCommand(systems, false),
			new PivotCommand(systems, Pivot.POSITION.DOWN)
		);

		
	}

	@Override
	public void end(boolean interrupted) {
		new PivotCommand(systems, POSITION.UP);
		super.end(interrupted);
	}

}