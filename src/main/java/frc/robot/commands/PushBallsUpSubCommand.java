package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

/**
 * @author Daniel Brubaker
 * @author Rishmita Rao
 */

public class PushBallsUpSubCommand extends ParallelCommandGroup {
    private final Feeder feeder;
	long startTime;
	
    public PushBallsUpSubCommand(Intake intake, Hopper hopper, Feeder feeder) {
		this.feeder = feeder;
        addCommands(
            new FeederCommand(feeder, true),
            new HopperCommand(hopper, false)
            // new IntakeCommand(intake, false)
        );
    }

	@Override
	public void initialize() {
		startTime = System.currentTimeMillis();
		super.initialize();
	}

    @Override
    public boolean isFinished() {
		
		return 0 == feeder.getBallCount() || System.currentTimeMillis() >= 2000 + startTime;
	
    }
}