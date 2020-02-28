package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;

/**
 * @author Ryan Hirasaki
 */
public class PivotCommand extends CommandBase {
    private final Pivot pivot;
    private final Pivot.POSITION position;

    public PivotCommand(Pivot pivot, Pivot.POSITION pos) {
        this.pivot = pivot;
        this.position = pos;

        assert (pos != null);

        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        pivot.setPivotLocation(position);
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