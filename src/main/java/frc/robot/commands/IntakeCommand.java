package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.team5431.titan.core.misc.Calc;

public class IntakeCommand extends CommandBase {
    private final Intake intake;
    private final boolean direction;

    public IntakeCommand(Intake intake, boolean reverse) {
        this.intake = intake;
        this.direction = reverse;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setIntakeFeedSpeed(direction ? 0.1 : -0.1);
	}

    @Override
    public void end(boolean interrupted) {
        // intake.setIntakeFeedSpeed(0); // FIXME: add a stop so the intake stops
    }

    @Override
    public boolean isFinished() {
        return true; // FIXME: wut, ok so intake is not running as the end function appears to have the priority
    }
}