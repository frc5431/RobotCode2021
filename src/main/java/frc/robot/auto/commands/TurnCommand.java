package frc.robot.auto.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.components.Drivebase;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.robot.Command;

public class TurnCommand extends Command<Robot> {

    private final double angle;
    private long hitTarget = -1;

    public TurnCommand(final double angle) {
        name = "TurnCommand";

        this.angle = angle;

        properties = String.format("Angle: %f", angle);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public CommandResult update(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();
        if (drivebase.getControlMode() == ComponentControlMode.MANUAL) {
            robot.getAuton().abort(robot);
            return CommandResult.CLEAR_QUEUE;
        }

        final double currentAngle = drivebase.getHeading();
        drivebase.driveMotionMagic(0, angle);

        if (Calc.approxEquals(currentAngle, angle, Constants.DRIVEBASE_ANGLE_TOLERANCE)) {
            return CommandResult.COMPLETE;
        } else {
            return CommandResult.IN_PROGRESS;
        }
    }

    @Override
    public void done(Robot robot) {
    }
}