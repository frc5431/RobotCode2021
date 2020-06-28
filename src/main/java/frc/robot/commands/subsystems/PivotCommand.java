package frc.robot.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Pivot;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class PivotCommand extends CommandBase {
    private final Pivot pivot;
    private Pivot.POSITION position;
    private Pivot.SPEED speed;

    public PivotCommand(Systems systems, Pivot.POSITION pos) {
        this.pivot = systems.getPivot();
        this.position = pos;

        assert (pos != null);

        addRequirements(pivot);
    }

    public PivotCommand(Systems systems, Pivot.SPEED speed) {
        this.pivot = systems.getPivot();
        this.speed = speed;

        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        Logger.l("Running Pivot Command!");
        if (position != null)
            pivot.setPivotLocation(position);
        else 
            pivot.setSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.l("Finished Pivot Command!");
    }

    @Override
    public boolean isFinished() {
        return Calc.approxEquals(pivot.error(), position.getValue(), 500);
    }
}