package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivebase;
import frc.team5431.titan.core.misc.Calc;
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

        addRequirements(drivebase);
    }

    @Override
    public void initialize() {
        limelight.setLEDState(LEDState.ON);
    }

    @Override
    public void execute() {
        limelight.getTable().getEntry("pipeline").setNumber(1);

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
        double xError = turnController.calculate(limelight.getX());
        double yError = positionController.calculate(limelight.getY());

        final boolean xInRange = Calc.approxEquals(xError, 0, Constants.LIMELIGHT_ERROR_RATE);
        final boolean yInRange = Calc.approxEquals(yError, 0, Constants.LIMELIGHT_ERROR_RATE);

        return xInRange && yInRange;
    }
}