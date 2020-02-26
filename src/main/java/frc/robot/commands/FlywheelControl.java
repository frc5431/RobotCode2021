package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.util.states.FlywheelState;

public class FlywheelControl extends CommandBase {
    private final Flywheel flywheel;
    private final Flywheel.Speeds speed;
    private final boolean stopOnDone;

    public FlywheelControl(Flywheel flywheel, Flywheel.Speeds speed) {
        this(flywheel, speed, true);
    }

    public FlywheelControl(Flywheel flywheel, Flywheel.Speeds speed, boolean stopOnDone) {
        this.flywheel = flywheel;
        this.speed = speed;
        this.stopOnDone = stopOnDone;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        flywheel.set(speed);
    }

    @Override
    public void execute() {
        flywheel.set(speed);    
    }

    @Override
    public void end(boolean interrupted) {
        if (stopOnDone)
            flywheel.set(Flywheel.Speeds.OFF);
    }
}