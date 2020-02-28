package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class FeederCommand extends CommandBase {
    private final Feeder feeder;
    private final boolean direction;
    private final double speed;

    public FeederCommand(Feeder feeder, boolean reverse) {
        this(feeder, 1, reverse);
    }

    public FeederCommand(Feeder feeder, double speed) {
        this(feeder, speed, false);
    }

    public FeederCommand(Feeder feeder, double speed, boolean reverse) {
        this.feeder = feeder;
        this.direction = reverse;
        this.speed = speed;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        feeder.set(direction ? speed : -speed);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.l("Feeder Command Done");
        feeder.set(0);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
}