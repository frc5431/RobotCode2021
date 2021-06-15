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
	long startTime;
	boolean done = false;
	
    public PushBallDownCommand(Systems systems) {
		this.feeder = systems.getFeeder();
    }

	@Override
	public void initialize() {
		startTime = System.currentTimeMillis();
		super.initialize();
	}

	@Override
	public void execute() {
		feeder.set(-0.4);	
		Logger.l("Pushing balls down");
		
	}

	@Override
	public void end(boolean interupted) {
		feeder.set(0);
		Logger.l("finished pushing balls down");
	}


    @Override
    public boolean isFinished() {
		return ((startTime + Constants.FEEDER_PUSH_BALL_DOWN) <= System.currentTimeMillis()) || feeder.getValueOfDIOSensor(3);
    }
}