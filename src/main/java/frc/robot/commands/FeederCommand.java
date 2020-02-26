package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class FeederCommand extends CommandBase {
    private final Feeder feeder;
    private final boolean direction;

    public FeederCommand(Feeder feeder, boolean reverse) {
        this.feeder = feeder;
        this.direction = reverse;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {
        feeder.set(direction ? 1 : -1);
	}

    @Override
    public void end(boolean interrupted) {
        feeder.set(0);
    }
}