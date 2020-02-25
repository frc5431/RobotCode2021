package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeControl extends CommandBase {
    private final Intake intake;
    private final double speed;

    public IntakeControl(Intake intake, double speed) {
        this.intake = intake;
        this.speed = speed;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
		intake.setIntakeSpeed(speed);
    }

    @Override
    public void execute() {
        intake.setIntakeSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntakeSpeed(0);
    }
}