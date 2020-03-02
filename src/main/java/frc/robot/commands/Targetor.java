package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.LimelightSubsystem;
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
    //private final LimelightSubsystem limelightSubsytem; 
    private final LimelightSubsystem.Positions position; 

    //private final PIDController turnController, positionController;

    /**
     * @param drivebase
     * @param limelight
     */
    public Targetor(Drivebase drivebase, Limelight limelight, LimelightSubsystem.Positions position) {
        this.drivebase = drivebase;
        this.limelight = limelight;
        //this.limelightSubsytem = limelightSubsytem; 
        this.position = position; 

        position.getPIDControllerTurn().setTolerance(Constants.LIMELIGHT_ERROR_RATE);

        addRequirements(drivebase);
        //addRequirements(limelightSubsytem);
        
    }

    @Override
    public void initialize() {
        Logger.l("Begin Targetor Command!");
        limelight.setPipeline(position.getPipeline());     
        limelight.setLEDState(LEDState.DEFAULT);   
    }

    // 20 ms loop
    @Override
    public void execute() {
        Logger.l("Execute Targetor Command!");

        limelight.setPipeline(0);

        double xError = position.getPIDControllerTurn().calculate(limelight.getX());
        // double yError = positionController.calculate(limelight.getY());

        drivebase.drivePercentageArcade(0, xError);
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted)
            Logger.l("Targetor Command Interuppted");
        limelight.setLEDState(LEDState.DEFAULT);
        limelight.setPipeline(position.OFF.getPipeline());
    }
    @Override
    public boolean isFinished() {
        boolean targetLocked = position.getPIDControllerTurn().atSetpoint();
        boolean limelightCanSee = limelight.getValid();

        return targetLocked && limelightCanSee;
    }
}