package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Balancer;
import frc.robot.subsystems.Flywheel;

public class BalancerCommand extends CommandBase {
    private final Balancer balancer;
    private final boolean direction;

    public BalancerCommand(Balancer flywheel, boolean left) {
        this.balancer = flywheel;
        this.direction = left;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
        // TODO May be backwards, check
        balancer.set(direction ? 1 : -1);
    }

    @Override
    public void end(boolean interrupted) {
        balancer.set(0);
    }
}