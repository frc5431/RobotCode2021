package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

/**
 * @author Daniel Brubaker
 * @author Rishmita Rao
 */

public class PushBallsUpSubCommand extends ParallelCommandGroup {
	private final Feeder feeder;
	private final Flywheel flywheel;
	long lastBallCountedTime = 0;
	
    public PushBallsUpSubCommand(Intake intake, Hopper hopper, Feeder feeder, Flywheel flywheel) {
		this.feeder = feeder;
		this.flywheel = flywheel;
        addCommands(
			new HopperCommand(hopper, feeder, flywheel, false),
			new FeederCommand(feeder, flywheel, true)
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
		}
		super.execute();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
	}

    @Override
    public boolean isFinished() {
		return lastBallCountedTime + 500 < System.currentTimeMillis();
    }
}