package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.team5431.titan.core.misc.Calc;

/**
 * @author Ryan Hirasaki
 */
public class FlywheelCommand extends CommandBase {
    private final Flywheel flywheel;
    private final Flywheel.Speeds speed;    
    private final Flywheel.Velocity velocity;    

    public FlywheelCommand(Flywheel flywheel, Flywheel.Speeds speed) {
        this.flywheel = flywheel;
        this.speed = speed;
        this.velocity = null;

        addRequirements(flywheel);
    }

    public FlywheelCommand(Flywheel flywheel, Flywheel.Velocity velocity) {
        this.flywheel = flywheel;
        this.velocity = velocity;
        this.speed = null;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        if (speed == null)
            flywheel.set(velocity);
        if (velocity == null)
            flywheel.set(speed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return Calc.approxEquals(flywheel.getError(), 0, 50);
    }
}