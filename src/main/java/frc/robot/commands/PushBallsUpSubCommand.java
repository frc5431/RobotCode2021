package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.*;

/**
 * @author Daniel Brubaker
 * @author Rishmita Rao
 */

public class PushBallsUpSubCommand extends ParallelCommandGroup {
	private final Feeder feeder;
	private final Hopper hopper; 
	private final Intake intake; 
	private final Flywheel flywheel;
	long lastBallCountedTime = 0;
	
    public PushBallsUpSubCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel) {
		this.feeder = feeder;
		this.hopper = hopper; 
		this.intake = intake; 
		this.flywheel = flywheel;
        addCommands(
			//new FeederCommand(feeder, true),
			new HopperCommand(hopper, feeder, flywheel, false)
            // new IntakeCommand(intake, false)
        );
    }

	@Override
	public void initialize() {
		lastBallCountedTime = System.currentTimeMillis(); 
		super.initialize();
	}

	@Override
	public void execute() {
		if(!feeder.isEmpty()){
			lastBallCountedTime = System.currentTimeMillis();
			//new IntakeCommand(intake,0); 
		}
		feeder.set(Constants.SHOOTER_FEEDER_DEFAULT_SPEED);
		super.execute();
	}

	@Override
	public void end(boolean interrupted) {
		feeder.set(0);
		super.end(interrupted);
	}

    @Override
    public boolean isFinished() {
		return lastBallCountedTime + 500 < System.currentTimeMillis();
    }
}