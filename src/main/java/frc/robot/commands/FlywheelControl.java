package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Flywheel.Speeds;

/**
 * @author Ryan Hirasaki
 */
public class FlywheelControl extends CommandBase {
    private final Flywheel flywheel;
    private final Flywheel.Speeds speed;    
    private final Flywheel.Velocity velocity;    

    public FlywheelControl(Flywheel flywheel, Flywheel.Speeds speed) {
        this.flywheel = flywheel;
        this.speed = speed;
        this.velocity = null;

        addRequirements(flywheel);
    }

    public FlywheelControl(Flywheel flywheel, Flywheel.Velocity velocity) {
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
        if (speed == null)
            flywheel.set(Flywheel.Velocity.OFF);
        if (velocity == null)
            flywheel.set(Flywheel.Speeds.OFF);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}