package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.FeederCommand;
import frc.robot.commands.HopperCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.PivotCommand;
import frc.robot.subsystems.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class FloorIntakeCommand extends ParallelCommandGroup {

	private final Feeder feeder;
	private final Intake intake;
	private final Hopper hopper;
	private final Pivot pivot;

	public FloorIntakeCommand(Intake intake, Hopper hopper, Pivot pivot, Feeder feeder, Flywheel flywheel) {
		this.feeder = feeder; //
		this.intake = intake; //
		this.hopper = hopper; //
		this.pivot = pivot; //

		addCommands(
			new IntakeCommand(intake, false),
			new HopperCommand(hopper, feeder, flywheel, false),
			new PivotCommand(pivot, Pivot.POSITION.DOWN)
		);

		
	}

}