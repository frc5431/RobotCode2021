package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class PivotCommand extends CommandBase {
    private final Pivot pivot;
    private Pivot.POSITION position;
    private double speed;

    public PivotCommand(Pivot pivot, Pivot.POSITION pos) {
        this.pivot = pivot;
        this.position = pos;

        assert (pos != null);

        addRequirements(pivot);
    }

    public PivotCommand(Pivot pivot, double speed) {
        this.pivot = pivot;
        this.speed = speed;

        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        Logger.l("Running Pivot Command!");
        if (position != null)
            pivot.setPivotLocation(position);
        pivot.setSpeed(speed);
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