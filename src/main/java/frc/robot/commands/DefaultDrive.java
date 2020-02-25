package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

public class DefaultDrive extends CommandBase {

    private final Drivebase drivebase;
    private final DoubleSupplier pow, ang;

    public DefaultDrive(Drivebase drivebase, DoubleSupplier power, DoubleSupplier angle) {
        this.drivebase = drivebase;
        this.pow = power;
        this.ang = angle;

        addRequirements(drivebase);
    }

    @Override
    public void execute() {
        drivebase.drivePercentageArcade(pow.getAsDouble(), ang.getAsDouble());
    }
}