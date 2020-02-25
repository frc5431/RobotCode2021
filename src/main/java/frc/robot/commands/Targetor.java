package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivebase;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;

public class Targetor extends CommandBase {
    
    private final Drivebase drivebase;
    private final Limelight limelight;

    private final PIDController turnController, positionController;

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
        limelight.setLEDState(LEDState.ON);
    }

    // 20 ms loop
    @Override
    public void execute() {
        limelight.getTable().getEntry("pipeline").setNumber(0);

        double xError = turnController.calculate(limelight.getX());
        double yError = positionController.calculate(limelight.getY());

        drivebase.drivePercentageArcade(yError, xError);
    }

    @Override
    public void end(boolean interrupted) {
        limelight.setLEDState(LEDState.OFF);
    }
    @Override
    public boolean isFinished() {
        return turnController.atSetpoint() && positionController.atSetpoint();
    }
}