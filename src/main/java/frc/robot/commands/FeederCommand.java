package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

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
    public boolean isFinished() {
        return true;
    }
}