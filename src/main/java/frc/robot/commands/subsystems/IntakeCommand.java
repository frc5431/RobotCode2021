package frc.robot.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Systems;
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

    public IntakeCommand(Systems systems, boolean reverse) {
        this(systems, Constants.INTAKE_DEFAULT_SPEED, reverse);
    }

    public IntakeCommand(Systems systems, double speed) {
        this(systems, speed, false);
    }

    public IntakeCommand(Systems systems, double speed, boolean reverse) {
        this.intake = systems.getIntake();
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