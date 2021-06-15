package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.Systems;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.misc.Logger;

/**
 * @author Daniel Brubaker
 * @author Rishmita Rao
 */

public class PushBallDownCommand extends CommandBase{
    private final Feeder feeder;
	
    public PushBallDownCommand(Systems systems) {
		this.feeder = systems.getFeeder();
    }

	@Override
	public void execute() {
		feeder.set(-Constants.SHOOTER_FEEDER_COMMAND_DOWN_SPEED);	
		Logger.l("Pushing balls down");
		
	}

	@Override
	public void end(boolean interupted) {
		feeder.set(0);
		Logger.l("finished pushing balls down");
	}


    @Override
    public boolean isFinished() {
		return !feeder.isTopBlocked();
    }
}