package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

public class FloorIntakeSuperCommand extends ParallelCommandGroup {
    public FloorIntakeSuperCommand(Intake intake, Hopper hopper, Flywheel flywheel) {
        addCommands(
            new PivotCommand(intake, Intake.POSITION.DOWN), 
            new IntakeCommand(intake, false), // FIXME: NO EXIT CONDITION
            new HopperCommand(hopper, false) // FIXME: NO EXIT CONDITION
        );

        // Requirements are not needed
        // addRequirements(intake, hopper, flywheel);
    }
}