package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

/**
 * @author Ryan Hirasaki
 */
public class StowSuperCommand extends ParallelCommandGroup {
	public StowSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Elevator elevator, Balancer balancer, Pivot pivot) {
		addCommands(
			// new PivotCommand(pivot, Pivot.POSITION.UP), // Brings the Intake Up
			new FlywheelCommand(flywheel, Flywheel.Speeds.OFF), // Stop The Flywheel
			new HopperCommand(hopper, 0.0, 0.0), // Stop The Hopper
			new FeederCommand(feeder, 0.0), // Stop The Feeder
			new IntakeCommand(intake, 0.0) // Stop The Intake
		);
	}
}