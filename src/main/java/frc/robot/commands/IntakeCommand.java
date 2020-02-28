package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

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
        this(intake, 0.1, reverse);
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
        if (interrupted)
            intake.setSpeed(0);
        // intake.setIntakeFeedSpeed(0); // FIXME: add a stop so the intake stops
    }

    @Override
    public boolean isFinished() {
        return true; // FIXME: wut, ok so intake is not running as the end function appears to have the priority
    }
}