package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.util.states.FlywheelState;

public class FlywheelControl extends CommandBase {
    private final Flywheel flywheel;
    private final Flywheel.Speeds speed;

    public FlywheelControl(Flywheel flywheel, FlywheelState stop) {
        this.flywheel = flywheel;
        this.speed = stop;

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
        flywheel.set(Flywheel.Speeds.OFF);
    }
}