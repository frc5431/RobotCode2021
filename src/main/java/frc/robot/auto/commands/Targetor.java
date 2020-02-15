package frc.robot.auto.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.auto.vision.TargetType;
import frc.robot.components.Drivebase;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;

public class Targetor extends Command<Robot> {

    private Limelight front;
    private final TargetType target;

    public Targetor(TargetType target) {
        this.target = target;

        this.name = "Targetor";
    }

    public Targetor(TargetType target, Limelight front) {
        this(target);

        this.front = front;
    }

    @Override
    public void init(Robot robot) {
        front = robot.getVision().getFrontLimelight();

        front.setLEDState(LEDState.ON);

        // TODO: add get and set pipeline in TitanUtil
        front.getTable().getEntry("pipeline").setNumber(target.getPipeline());
    }

    @Override
    public CommandResult update(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();

        final double x = front.getX();
        final double y = front.getY();

        final boolean xInRange = Calc.approxEquals(x, 0, Constants.LIMELIGHT_ERROR_RATE);
        final boolean yInRange = Calc.approxEquals(y, 0, Constants.LIMELIGHT_ERROR_RATE);

        // If x and y are bad, then fix
        if ((!xInRange) && (!yInRange)) {
            drivebase.drivePercentageArcade(y, x); // TODO: Test the error, most likely needs to multiplied.
            return CommandResult.IN_PROGRESS;
        }
        // if only x is bad then fix x
        else if (!xInRange && yInRange) {
            drivebase.drivePercentageArcade(0, x); // TODO: Test the error, most likely needs to multiplied.
            return CommandResult.IN_PROGRESS;
        }
        // if only y is bad then fix y
        else if (xInRange && !yInRange) {
            drivebase.drivePercentageArcade(y, 0); // TODO: Test the error, most likely needs to multiplied.
            return CommandResult.IN_PROGRESS;
        }
        else {
            return CommandResult.COMPLETE;
        }
    }

    @Override
    public void done(Robot robot) {
        front.setLEDState(LEDState.OFF);
    }
}