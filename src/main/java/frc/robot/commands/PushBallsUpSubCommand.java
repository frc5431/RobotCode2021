package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.Systems;
import frc.robot.subsystems.*;
import frc.robot.util.ShootPosition;
import frc.robot.commands.subsystems.*;

/**
 * @author Daniel Brubaker
 * @author Rishmita Rao
 * @author Colin Wong
 */

public class PushBallsUpSubCommand extends ParallelCommandGroup {
	private final Feeder feeder;
	long lastBallCountedTime = 0;
	
    public PushBallsUpSubCommand(Systems systems, ShootPosition pos, boolean rpmWait) {
		this.feeder = systems.getFeeder();
        addCommands(
			new HopperCommand(systems, false),
			new FeederCommand(systems, ((pos == ShootPosition.CLOSE) ? Constants.SHOOTER_FEEDER_DEFAULT_SPEED : ((pos == ShootPosition.FAR) ? Constants.SHOOTER_FEEDER_FAR_DEFAULT_SPEED : Constants.SHOOTER_FEEDER_AUTON_DEFAULT_SPEED)), true, rpmWait)
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