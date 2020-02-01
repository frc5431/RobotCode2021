package frc.robot.auto.commands;

import frc.robot.Robot;
import frc.robot.components.Drivebase;
import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Command;

public class DriveToArcCommand extends Command<Robot> {

    private final double distance, angle;

    public DriveToArcCommand(final double distance, final double angle) {
        name = "DriveToArcCommand";

        this.distance = distance;
        this.angle = angle;

        properties = String.format("Distance: %f, Angle: %f", distance, angle);

    }

    @Override
    public void init(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();
    }

    @Override
    public CommandResult update(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();
        if (drivebase.getControlMode() == ComponentControlMode.MANUAL) {
            robot.getAuton().abort(robot);
            return CommandResult.CLEAR_QUEUE;
        }

        drivebase.driveMotionMagic(distance, angle);
        if(hasCompleted(robot)) {

        } else {

        }
        return null;
    }

    @Override
    public void done(Robot robot) {
    }

    private boolean hasCompleted(final Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();
        final double angle = drivebase.getHeading();

        return false; //TODO finish
    }
}