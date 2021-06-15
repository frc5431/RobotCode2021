package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class FlywheelTriggerCommand extends CommandBase {

	public static boolean KILL = false;

    private final Flywheel flywheel;
    // private final Flywheel.Speeds speed;    
    private final Flywheel.Velocity velocity;  

    private final DoubleSupplier pow;

    // public FlywheelCommand(Systems systems, Flywheel.Speeds speed) {
    //     this.flywheel = systems.getFlywheel();
    //     this.speed = speed;
    //     this.velocity = null;

    //     addRequirements(flywheel);
    // }

    public FlywheelTriggerCommand(Systems systems, Flywheel.Velocity baseVel, DoubleSupplier pow) {
        this.flywheel = systems.getFlywheel();
        this.velocity = baseVel;
        // this.speed = null;
        this.pow = pow;

        addRequirements(flywheel);
    }

    private double map(double v, double in_min, double in_max, double out_min, double out_max) {
        return (v - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    @Override
    public void initialize() {
		KILL = false;
    }

    @Override
    public void execute() {
        flywheel.setSpeed(ControlMode.Velocity, map(pow.getAsDouble(), -1.0, 1.0, 0.0, velocity.getSpeed()*1.25));
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