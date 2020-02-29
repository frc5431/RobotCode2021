package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;
import frc.team5431.titan.core.misc.Logger;

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
        Logger.l("Running Pivot Command!");
        pivot.setPivotLocation(position);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.l("Finished Pivot Command!");
    }

    @Override
    public boolean isFinished() {
        return pivot.atLocation();
    }
}