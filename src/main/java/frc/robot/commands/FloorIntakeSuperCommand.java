package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class FloorIntakeSuperCommand extends SequentialCommandGroup {
    public FloorIntakeSuperCommand(Intake intake, Hopper hopper, Flywheel flywheel) {
        addCommands(
            new PivotCommand(intake, Intake.POSITION.DOWN), 
            new IntakeCommand(intake, false),
            new HopperCommand(hopper, false)
        );

        addRequirements(intake, hopper, flywheel);
    }
}