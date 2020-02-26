package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.misc.Logger;

public class FloorIntakeSuperCommand extends SequentialCommandGroup {
    public FloorIntakeSuperCommand(Intake intake, Hopper hopper, Flywheel flywheel) {
        addCommands(
            // new PivotCommand(intake, Intake.POSITION.DOWN), 
            new IntakeCommand(intake, false), // FIXME: NO EXIT CONDITION
            new HopperCommand(hopper, false) // FIXME: NO EXIT CONDITION
        );

        // Requirements are not needed
        // addRequirements(intake, hopper, flywheel);
    }

    @Override
    public void execute() {
        Logger.l("AAAAAAAAAAAAAAAAAAA");
        super.execute();
    }
}