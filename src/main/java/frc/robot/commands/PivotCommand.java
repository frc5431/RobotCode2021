package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;

public class PivotCommand extends CommandBase {
    private final Pivot intake;
    private final Pivot.POSITION position;

    public PivotCommand(Pivot intake, Pivot.POSITION pos) {
        this.intake = intake;
        this.position = pos;

        assert (pos != null);

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setPivotLocation(position);
    }

    // @Override
    // public void end(boolean interrupted) {
    // intake.setIntakeFeedSpeed(0);
    // }

    // @Override
    // public boolean isFinished() {
    // return intake.atLocation();
    // }

    @Override
    public boolean isFinished() {
        return true;
    }
}