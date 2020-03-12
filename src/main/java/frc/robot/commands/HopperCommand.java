package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Systems;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Hopper;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class HopperCommand extends CommandBase {
	private final Hopper hopper;
	private final Feeder feeder;
    private final boolean direction;
	private final double speedLeft, speedRight;
	private final Flywheel flywheel;

    public HopperCommand(Systems systems, boolean reverse) {
        this(systems, Constants.HOPPER_LEFT_SPEED, Constants.HOPPER_RIGHT_SPEED, reverse);
    }

    public HopperCommand(Systems systems, double speedLeft, double speedRight) {
        this(systems, speedLeft, speedRight, false);
    }

    public HopperCommand(Systems systems, double speedLeft, double speedRight, boolean reverse) {
        this.hopper = systems.getHopper();
        this.direction = reverse;
        this.speedLeft = speedLeft;
		this.speedRight = speedRight;
		this.feeder = systems.getFeeder();
		this.flywheel = systems.getFlywheel();

        addRequirements(hopper);
    }

    @Override
    public void initialize() {
        hopper.set(direction ? speedLeft : -speedLeft, direction ? speedRight : -speedRight);
	}

	@Override
	public void execute() {
		if (feeder.isFull() && (flywheel.getSpeed() == 0)) {
			hopper.set(0,0);
			// Logger.l("Hopper Stop");
			// Logger.l("Feeder is full; disabling hopper");
		} else {
			hopper.set(direction ? speedLeft : -speedLeft, direction ? speedRight : -speedRight);
			// Logger.l("Running Hopper Command");
		}

		// Logger.l("%b",feeder.isFull());
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