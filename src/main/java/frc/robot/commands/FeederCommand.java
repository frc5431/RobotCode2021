package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class FeederCommand extends CommandBase {
    private final Feeder feeder;
    private final boolean direction;
	private final double speed;
	private final Flywheel flywheel;

    public FeederCommand(Feeder feeder, Flywheel flywheel, boolean reverse) {
        this(feeder, flywheel, 1, reverse);
    }

    public FeederCommand(Feeder feeder, Flywheel flywheel, double speed) {
        this(feeder, flywheel, speed, false);
    }

    public FeederCommand(Feeder feeder, Flywheel flywheel, double speed, boolean reverse) {
        this.feeder = feeder;
        this.direction = reverse;
		this.speed = speed;
		this.flywheel = flywheel;

        addRequirements(feeder);
    }

    @Override
    public void initialize() {

	}
	
	@Override
	public void execute() {
		if(direction) {
			if (flywheel.atVelocity()) {
				feeder.set(speed);
			} else {
				Logger.l("Flywheel not at speed, not pushing up!");
				feeder.set(0);
			}
		}
		else {
			feeder.set(-speed);
		}
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