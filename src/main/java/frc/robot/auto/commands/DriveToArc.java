package frc.robot.auto.commands;

import frc.robot.Robot;
import frc.robot.components.Drivebase;
import frc.team5431.titan.core.misc.Calc;
import frc.team5431.titan.core.robot.Command;

public class DriveToArc extends Command<Robot> {

    private final double distance, angle;

    private double startAngle, finalAngle;
    
    public DriveToArc(final double power, final double angle) {
        this.distance = power;
        this.angle = angle;

        this.properties = String.format("DriveToArc Angle: %.2f, Distance: %.2f", angle, power);
    }

    @Override
    public void init(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();

        drivebase.resetSensors();
        startAngle = drivebase.getHeading();
        finalAngle = startAngle + angle;
    
    }

    @Override
    public CommandResult update(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();
        final double currAngle = drivebase.getHeading();

        if (Calc.approxEquals(currAngle, finalAngle, 20)) {
            return CommandResult.COMPLETE;
        }
        else {
            drivebase.driveMotionMagic(distance, finalAngle);
            return CommandResult.IN_PROGRESS;
        }
    }

    @Override
    public void done(Robot robot) {
    }
}