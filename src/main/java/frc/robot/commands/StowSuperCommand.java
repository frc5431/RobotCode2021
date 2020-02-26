package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class StowSuperCommand extends SequentialCommandGroup {
	public StowSuperCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel) {
		addCommands(

		);
	}
}