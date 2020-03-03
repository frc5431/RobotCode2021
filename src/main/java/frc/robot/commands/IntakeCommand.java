package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.team5431.titan.core.misc.Logger;

/**
 * IntakeCommand.
 * 
 * This Command suould allow for control of the intake and be able to change the speed and reverse if needed.
 * 
 * If Going in reverse without a speed, the default speed is set to 0.1, aka 10%.
 * 
 * @author Ryan Hirasaki
 */
public class IntakeCommand extends CommandBase {
    private final Intake intake;
    private final boolean direction;
    private final double speed;

    public IntakeCommand(Intake intake, boolean reverse) {
        this(intake, Constants.INTAKE_DEFAULT_SPEED, reverse);
    }

    public IntakeCommand(Intake intake, double speed) {
        this(intake, speed, false);
    }

    public IntakeCommand(Intake intake, double speed, boolean reverse) {
        this.intake = intake;
        this.direction = reverse;
        this.speed = speed;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(direction ? speed : -speed);
	}

    @Override
    public void end(boolean interrupted) {
        Logger.l("Intake Command Done");
        intake.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}