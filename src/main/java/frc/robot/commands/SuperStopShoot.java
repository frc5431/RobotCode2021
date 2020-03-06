package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class SuperStopShoot extends ParallelCommandGroup {
	private Feeder feeder;
	public SuperStopShoot(Feeder feeder, Intake intake, Hopper hopper, Flywheel flywheel) {
		this.feeder = feeder;
		addCommands(
			new FeederCommand(feeder, flywheel, 0, false),
			new IntakeCommand(intake, 0),
			new HopperCommand(hopper, feeder, flywheel, 0, 0),
			new FlywheelCommand(flywheel, Flywheel.Velocity.OFF)
		);
	}

	@Override
	public void end(boolean interrupted) {
		feeder.resetVars();
		super.end(interrupted);
	}
}