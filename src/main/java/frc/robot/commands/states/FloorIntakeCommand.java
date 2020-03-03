package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.*;

/**
 * @author Colin Wong
 * @author Ryan Hirasaki
 */
public class FloorIntakeCommand extends CommandBase {

	private final Feeder feeder;
	private final Intake intake;
	private final Hopper hopper;
	private final Pivot pivot;

	private boolean hasThree = false;

	public FloorIntakeCommand(Intake intake, Hopper hopper, Pivot pivot, Feeder feeder) {
		this.feeder = feeder; //
		this.intake = intake; //
		this.hopper = hopper; //
		this.pivot = pivot; //

		addRequirements(intake, hopper, pivot, feeder);
	}

	private void updateHasThree() {
		hasThree = feeder.getBallCount() >= 3;
	}

	@Override
	public void initialize() {
		hasThree = false;
		updateHasThree();
		pivot.setPivotLocation(Pivot.POSITION.DOWN);

	}

	@Override
	public void execute() {
		updateHasThree();
		if (!hasThree) {
			hopper.set(-Constants.HOPPER_LEFT_SPEED, -Constants.HOPPER_RIGHT_SPEED);
		} else {
			hopper.set(0, 0);
		}
		intake.setSpeed(-Constants.INTAKE_DEFAULT_SPEED);
	}

	@Override
	public void end(boolean interrupted) {
		intake.setSpeed(0);
		pivot.setPivotLocation(Pivot.POSITION.UP);
		hopper.set(0, 0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}