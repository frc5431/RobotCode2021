package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Flywheel.Velocity;

/**
 * Runs flywheel at a speed specified by the passed DoubleSupplier
 * 
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class FlywheelTriggerCommand extends CommandBase {

	public static boolean KILL = false;

    private final Flywheel flywheel;
    private final double velocity;  

    private final DoubleSupplier pow;

    /**
     * @param systems the robot's systems
     * @param maxVel the max velocity
     * @param pow
     */
    public FlywheelTriggerCommand(Systems systems, double maxVel, DoubleSupplier pow) {
        this.flywheel = systems.getFlywheel();
        this.velocity = maxVel;
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
        // flywheel.setSpeed(ControlMode.Velocity, map(pow.getAsDouble(), -1.0, 1.0, 0.0, velocity));
    }

    @Override
    public void end(boolean interrupted) {
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