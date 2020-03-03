package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Flywheel.Speeds;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.robot.subsystems.LimelightSubsystem.Positions;
import frc.team5431.titan.core.vision.Limelight;
import frc.robot.commands.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class ShootSuperCommandClose extends SequentialCommandGroup {
	//private final SuperStopShoot stop;
	private final Feeder feeder;
	private final Flywheel flywheel; 
	private final Hopper hopper; 

    public ShootSuperCommandClose(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel, Drivebase drivebase, Limelight limelight) {
		//stop = new SuperStopShoot(feeder, intake, hopper, flywheel);
		this.feeder = feeder; 
		this.flywheel = flywheel;
		this.hopper = hopper; 

        addCommands(
            // Target
            new Targetor(drivebase, limelight, Positions.HALF),
             // Bring up to speed
            new FlywheelCommand(flywheel, Velocity.HALF), //Waits till up to speed
            // Push Balls. Keep running until current command is interuppted
            new PushBallsUpSubCommand(intake, hopper, feeder)
		);

		// andThen(
		// 	new SuperStopShoot(feeder, intake, hopper, flywheel)
		// );
	}
	
	@Override
	public void end(boolean interrupted) {
		feeder.resetVars(); 
		feeder.set(0); 
		flywheel.set(Velocity.OFF);
		hopper.set(0, 0);

	}
}