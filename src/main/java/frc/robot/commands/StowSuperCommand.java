package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class StowSuperCommand extends SequentialCommandGroup {
	public StowSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Elevator elevator, Balancer balancer, Pivot pivot) {
		addCommands(
			new PivotCommand(pivot, Pivot.POSITION.UP), // Brings the Intake Up
			new FlywheelControl(flywheel, Flywheel.Speeds.OFF), // Stop The Flywheel
			new HopperCommand(hopper, 0.0), // Stop The Hopper
			new FeederCommand(feeder, 0.0), // Stop The Feeder
			new IntakeCommand(intake, 0.0) // Stop The Intake
		);
	}
}