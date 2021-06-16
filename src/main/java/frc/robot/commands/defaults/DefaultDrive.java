package frc.robot.commands.defaults;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Drivebase;

/**
 * @author Ryan Hirasaki
 */
public class DefaultDrive extends CommandBase {

    private final Drivebase drivebase;
    private final DoubleSupplier pow, ang;

    public DefaultDrive(Systems systems, DoubleSupplier power, DoubleSupplier angle) {
        this.drivebase = systems.getDrivebase();
        this.pow = power;
        this.ang = angle;

        addRequirements(drivebase);
    }

    @Override
    public void execute() {
        drivebase.drivePercentageArcade(pow.getAsDouble(), ang.getAsDouble());
    }
}