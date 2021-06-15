package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.Systems;
import frc.robot.commands.subsystems.*;

/**
 * @author Ryan Hirasaki
 */
public class StowSuperCommand extends ParallelCommandGroup {
	public StowSuperCommand(Systems systems) {
		addCommands(
			new PivotCommand(systems, Pivot.POSITION.UP, false), // Brings the Intake Up
			new FlywheelCommand(systems, Flywheel.Velocity.OFF), // Stop The Flywheel
			new HopperCommand(systems, 0.0, 0.0), // Stop The Hopper
			new FeederCommand(systems, 0.0, false), // Stop The Feeder
			new IntakeCommand(systems, 0.0) // Stop The Intake
		);
	}	
}