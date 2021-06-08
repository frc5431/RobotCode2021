package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Systems;
import frc.robot.subsystems.Drivebase;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;

/**
 * @author Ryan Hirasaki
 * @author Rishmita Rao
 */
public class Targetor extends CommandBase {

	private final Drivebase drivebase;
	private final Limelight limelight;
	private boolean isLocked; 
	private long startLockTime; 

	// private final LimelightSubsystem limelightSubsytem;
	// private final LimelightSubsystem.Positions position;

	// private final PIDController turnController, positionController;

	/**
	 * @param drivebase
	 * @param limelight
	 */
	public Targetor(Systems systems, Limelight limelight) {
		this.drivebase = systems.getDrivebase();
		this.limelight = limelight;
		// this.limelightSubsytem = limelightSubsytem;
		// this.position = position;

		Constants.LIMELIGHT_PID.setSetpoint(0);
		Constants.LIMELIGHT_PID.setTolerance(Constants.LIMELIGHT_ERROR_RATE);
		// position.getPIDControllerTurn().setTolerance(0);

		addRequirements(systems.getDrivebase());
		// addRequirements(limelightSubsytem);

	}

	@Override
	public void initialize() {
		Logger.l("Begin Targetor Command!");
		limelight.setPipeline(Constants.LIMELIGHT_PIPELINE_ON);
		limelight.setLEDState(LEDState.DEFAULT);
		SmartDashboard.putBoolean("Limelight Command Finished", false);
		isLocked = false; 
	}

	public boolean isValid(){
		double aspectRatio = limelight.getTable().getEntry("thor").getDouble(0.0)/limelight.getTable().getEntry("tvert").getDouble(0.0); 
		SmartDashboard.putNumber("Limelight Aspect Ratio", aspectRatio);
		return aspectRatio >= 1 && limelight.getValid(); 

	}

	public boolean isLocked(){
		boolean targetLocked = Math.abs(Constants.LIMELIGHT_PID.calculate(limelight.getX())) <= Constants.LIMELIGHT_ERROR_RATE;
		return targetLocked; 
	}

	// 20 ms loop
	@Override
	public void execute() {

		limelight.setPipeline(Constants.LIMELIGHT_PIPELINE_ON);
		

		double xError = Constants.LIMELIGHT_PID.calculate(limelight.getX());
		SmartDashboard.putNumber("Limelight Error X", xError);
		SmartDashboard.putNumber("Limelight Pipeline", Constants.LIMELIGHT_PIPELINE_ON);

		// double yError = positionController.calculate(limelight.getY());
		if(isValid()){
			drivebase.drivePercentageArcade(0, xError + (0.15 * Math.signum(xError)));
		}
		else{
			drivebase.drivePercentageArcade(0, 0);
		}

	}


	@Override
	public void end(boolean interrupted) {
		if (interrupted)
			Logger.l("Targetor Command Interuppted");
		else
			Logger.l("Targetor Command Finished");
		limelight.setLEDState(LEDState.DEFAULT);
		limelight.setPipeline(Constants.LIMELIGHT_PIPELINE_OFF);
		SmartDashboard.putNumber("Limelight Pipeline", Constants.LIMELIGHT_PIPELINE_OFF); 
		SmartDashboard.putBoolean("Limelight Command Finished", true); 

    }
    @Override
    public boolean isFinished() {

		boolean fullFinish = isLocked() && isValid();
		if(fullFinish){
			if(!isLocked){
				isLocked = true;
				startLockTime = System.currentTimeMillis(); 
			}
			else{
				return startLockTime + 200 >= System.currentTimeMillis(); 
			}
		}
		else{
			isLocked = false; 
		}
		return false;
    }
}