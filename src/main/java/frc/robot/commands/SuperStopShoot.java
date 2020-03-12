package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Systems;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;

public class SuperStopShoot extends ParallelCommandGroup {
	private Feeder feeder;
	public SuperStopShoot(Systems systems) {
		this.feeder = systems.getFeeder();
		addCommands(
			new FeederCommand(systems, 0, false),
			new IntakeCommand(systems, 0),
			new HopperCommand(systems, 0, 0),
			new FlywheelCommand(systems, Flywheel.Velocity.OFF)
		);
	}

	@Override
	public void end(boolean interrupted) {
		feeder.resetVars();
		super.end(interrupted);
	}
}