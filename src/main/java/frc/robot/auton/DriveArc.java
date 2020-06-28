package frc.robot.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;

/**
 * @author Ryan Hirasaki
 */
public class DriveArc extends CommandBase {

    private final Systems systems;
    private final double angle, distance;

    public DriveArc(Systems systems, double angle, double distance) {
        this.systems = systems;
        this.angle = angle;
        this.distance = distance;

        addRequirements(systems.getDrivebase());
    }

    @Override
    public void initialize() {
        systems.getDrivebase().resetSensors();
        systems.getDrivebase().driveMotionMagic(distance, angle);
    }

    @Override
    public void end(boolean interrupted) {
        systems.getDrivebase().drivePercentageTank(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false; // FIXME: Will Run Continiously
    }
}