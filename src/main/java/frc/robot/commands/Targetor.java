package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivebase;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;

/**
 * @author Ryan Hirasaki
 */
public class Targetor extends CommandBase {
    
    private final Drivebase drivebase;
    private final Limelight limelight;

    private final PIDController turnController, positionController;

    /**
     * @param drivebase
     * @param limelight
     */
    public Targetor(Drivebase drivebase, Limelight limelight) {
        this.drivebase = drivebase;
        this.limelight = limelight;

        turnController = new PIDController(0.05, 0, 0);
        positionController = new PIDController(0.05, 0, 0);

        
        turnController.setTolerance(Constants.LIMELIGHT_ERROR_RATE);
        positionController.setTolerance(Constants.LIMELIGHT_ERROR_RATE);

        addRequirements(drivebase);
    }

    @Override
    public void initialize() {
        Logger.l("Begin Targetor Command!");
        limelight.setPipeline(0);     
        limelight.setLEDState(LEDState.DEFAULT);   
    }

    // 20 ms loop
    @Override
    public void execute() {
        Logger.l("Execute Targetor Command!");

        limelight.setPipeline(0);

        double xError = turnController.calculate(limelight.getX());
        // double yError = positionController.calculate(limelight.getY());

        drivebase.drivePercentageArcade(0, xError);
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted)
            Logger.l("Targetor Command Interuppted");
        limelight.setLEDState(LEDState.DEFAULT);
        limelight.setPipeline(9);
    }
    @Override
    public boolean isFinished() {
        boolean targetLocked = turnController.atSetpoint();
        boolean limelightCanSee = limelight.getValid();

        return targetLocked && limelightCanSee;
    }
}