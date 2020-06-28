package frc.robot.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;

/**
 * @author Ryan Hirasaki
 */
public class BalancerCommand extends CommandBase {
    private final Systems systems;
    private final boolean direction;

    public BalancerCommand(Systems systems, boolean left) {
        this.systems = systems;
        this.direction = left;

        addRequirements(systems.getBalancer());
    }

    @Override
    public void initialize() {
        systems.getBalancer().set(direction ? 1 : -1);
    }

    @Override
    public void end(boolean interrupted) {
        systems.getBalancer().set(0);
    }
}