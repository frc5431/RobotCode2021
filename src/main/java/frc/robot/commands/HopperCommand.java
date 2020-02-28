package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class HopperCommand extends CommandBase {
    private final Hopper hopper;
    private final boolean direction;
    private final double speed;

    public HopperCommand(Hopper hopper, boolean reverse) {
        this(hopper, 1, reverse);
    }

    public HopperCommand(Hopper hopper, double speed) {
        this(hopper, speed, false);
    }

    public HopperCommand(Hopper hopper, double speed, boolean reverse) {
        this.hopper = hopper;
        this.direction = reverse;
        this.speed = speed;

        addRequirements(hopper);
    }

    @Override
    public void initialize() {
        hopper.set(direction ? speed : -speed);
	}

    @Override
    public void end(boolean interrupted) {
        Logger.l("Hopper Command Done");
        hopper.set(0);

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}