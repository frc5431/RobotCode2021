package frc.robot.commands.defaults;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Drivebase;

/**
 * @author Ryan Hirasaki
 */
public class DefaultDrive extends CommandBase {

    public static enum DriveMode {
        ARCADE, TANK
    }

    private final Drivebase drivebase;
    private final Supplier<DriveMode> modeSupplier;
    private final DoubleSupplier pow, ang, left, right;

    public DefaultDrive(Systems systems, Supplier<DriveMode> mode,
        DoubleSupplier power, DoubleSupplier angle,     // arcade
        DoubleSupplier left, DoubleSupplier right) {    // tank
        this.drivebase = systems.getDrivebase();
        this.modeSupplier = mode;
        this.pow = power;
        this.ang = angle;
        this.left = left;
        this.right = right;

        addRequirements(drivebase);
    }

    @Override
    public void execute() {
        switch (modeSupplier.get()) {
            case TANK:
                drivebase.drivePercentageTank(left.getAsDouble(), right.getAsDouble());
            case ARCADE:
            default:
                drivebase.drivePercentageArcade(pow.getAsDouble(), ang.getAsDouble());
        }
    }
}
