package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;

public class SuperStopShoot extends ParallelCommandGroup {
	public SuperStopShoot(Feeder feeder, Intake intake, Hopper hopper) {
		addCommands(
			new FeederCommand(feeder, 0),
			new IntakeCommand(intake, 0),
			new HopperCommand(hopper, 0, 0)
		);
	}
}