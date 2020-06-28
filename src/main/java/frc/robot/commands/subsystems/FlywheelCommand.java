package frc.robot.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 */
public class FlywheelCommand extends CommandBase {

	public static boolean KILL = false;

    private final Flywheel flywheel;
    private final Flywheel.Speeds speed;    
    private final Flywheel.Velocity velocity;  

    public FlywheelCommand(Systems systems, Flywheel.Speeds speed) {
        this.flywheel = systems.getFlywheel();
        this.speed = speed;
        this.velocity = null;

        addRequirements(flywheel);
    }

    public FlywheelCommand(Systems systems, Flywheel.Velocity velocity) {
        this.flywheel = systems.getFlywheel();
        this.velocity = velocity;
        this.speed = null;

        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
		KILL = false;
        if (speed == null)
            flywheel.set(velocity);
        if (velocity == null)
            flywheel.set(speed);
    }

    @Override
    public void end(boolean interrupted) {
		Logger.l("Flywheel Command Done");
		flywheel.set(Velocity.OFF);
	}
	
	@Override
	public boolean isFinished() {
		if(KILL) {
			KILL = false;
			return true;
		}
		else {
			return false;
		}
	}


}