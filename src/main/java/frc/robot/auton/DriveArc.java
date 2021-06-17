package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

/**
 * @author Ryan Hirasaki
 */
public class DriveArc extends CommandBase {

    private final Drivebase drivebase;
    private final double angle, distance;

    public DriveArc(Drivebase drivebase, double angle, double distance) {
        this.drivebase = drivebase;
        this.angle = angle;
        this.distance = distance;

        addRequirements(drivebase);
    }

    @Override
    public void initialize() {
        drivebase.resetSensors();
        drivebase.driveMotionMagic(distance, angle);
    }

    @Override
    public void end(boolean interrupted) {
        drivebase.drivePercentageTank(0, 0);
    }

    @Override
    public boolean isFinished() {
        return drivebase.reachedMotionMagicTarget();
    }
}