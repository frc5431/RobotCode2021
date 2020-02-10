package frc.robot.auto.commands;

import frc.robot.util.ComponentControlMode;
import frc.team5431.titan.core.robot.Command;
import frc.robot.Robot;
import frc.robot.components.Drivebase;

/**
 * This Command is used to drive foward and stop after so many milliseconds. It
 * is not recommended to use time based driving but is here if needed in the
 * future.
 * 
 * @author Liav Turkia
 * @since Febuary 10th, 2019
 * 
 *        Liav is an alumni, this code is from when he was a senior.
 * 
 * @author Ryan Hirasaki
 * @since Febuary 9th, 2020
 * 
 *        Ryan backported this to the 2020 robot.
 */
public class DriveCommand extends Command<Robot> {
    private final double left, right;
    private final long time;

    public DriveCommand(final double left, final double right, final long time) {
        name = "DriveCommand";

        this.left = left;
        this.right = right;

        this.time = time;

        properties = String.format("Left: %f; Right: %f; Time: %d", left, right, time);
    }

    @Override
    public void init(final Robot robot) {
    }

    @Override
    public CommandResult update(final Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();

        if (drivebase.getControlMode() == ComponentControlMode.MANUAL) {
            robot.getAuton().abort(robot);
            return CommandResult.CLEAR_QUEUE;
        }

        drivebase.drivePercentageTank(left, right);

        if (System.currentTimeMillis() > startTime + time) {
            return CommandResult.COMPLETE;
        } else {
            return CommandResult.IN_PROGRESS;
        }
    }

    @Override
    public void done(final Robot robot) {
    }
}