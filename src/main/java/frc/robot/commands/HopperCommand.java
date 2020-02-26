package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;

public class HopperCommand extends CommandBase {
    private final Hopper hopper;
    private final boolean direction;

    public HopperCommand(Hopper hopper, boolean reverse) {
        this.hopper = hopper;
        this.direction = reverse;

        addRequirements(hopper);
    }

    @Override
    public void initialize() {
        hopper.set(direction ? 1 : -1);
	}

    @Override
    public void end(boolean interrupted) {
        hopper.set(0);
    }
}