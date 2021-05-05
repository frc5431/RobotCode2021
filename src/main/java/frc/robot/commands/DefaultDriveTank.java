package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Drivebase;

/**
 * @author Ryan Hirasaki
 */
public class DefaultDriveTank extends CommandBase {

    private final Drivebase drivebase;
    private final DoubleSupplier left, right;

    public DefaultDriveTank(Systems systems, DoubleSupplier left, DoubleSupplier right) {
        this.drivebase = systems.getDrivebase();
        this.left = left;
        this.right = right;

        addRequirements(drivebase);
    }

    @Override
    public void execute() {
        drivebase.tankDrive(left.getAsDouble(), right.getAsDouble());
    }
}