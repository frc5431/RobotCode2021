package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class StowSuperCommand extends SequentialCommandGroup {
	public StowSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Elevator elevator, Balancer balancer) {
		addCommands(
			new PivotCommand(intake, Intake.POSITION.UP),
			// new ElevatorCommand(elevator, ClimberState.TOP),
			new FlywheelControl(flywheel, Flywheel.Speeds.OFF, true)
		);

		addRequirements(intake, hopper, feeder, flywheel, balancer);
	}
}