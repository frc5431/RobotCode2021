package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

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
        intake.setIntakeFeedSpeed(direction ? 1 : -1);
	}

    @Override
    public void end(boolean interrupted) {
        intake.setIntakeFeedSpeed(0);
    }
}