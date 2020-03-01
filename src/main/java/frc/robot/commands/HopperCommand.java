package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Hopper;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class HopperCommand extends CommandBase {
    private final Hopper hopper;
    private final boolean direction;
    private final double speedLeft, speedRight;

    public HopperCommand(Hopper hopper, boolean reverse) {
        this(hopper, Constants.HOPPER_LEFT_SPEED, Constants.HOPPER_RIGHT_SPEED, reverse);
    }

    public HopperCommand(Hopper hopper, double speedLeft, double speedRight) {
        this(hopper, speedLeft, speedRight, false);
    }

    public HopperCommand(Hopper hopper, double speedLeft, double speedRight, boolean reverse) {
        this.hopper = hopper;
        this.direction = reverse;
        this.speedLeft = speedLeft;
        this.speedRight = speedRight;

        addRequirements(hopper);
    }

    @Override
    public void initialize() {
        hopper.set(direction ? speedLeft : -speedLeft, direction ? speedRight : -speedRight);
	}

    @Override
    public void end(boolean interrupted) {
        Logger.l("Hopper Command Done");
        hopper.set(0, 0);

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}