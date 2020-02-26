package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class PivotCommand extends CommandBase {
    private final Intake intake;
    private final Intake.POSITION position;

    public PivotCommand(Intake intake, Intake.POSITION pos) {
        this.intake = intake;
        this.position = pos;

		assert (pos != null);

        addRequirements(intake);
    }

    @Override
    public void initialize() {
		intake.setPivotLocation(position);
	}

    @Override
    public void end(boolean interrupted) {
        intake.setIntakeFeedSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return intake.atLocation();
    }
}