package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Drivebase;
import frc.team5431.titan.core.misc.Logger;

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
        double _pow = pow.getAsDouble();
        double _ang = ang.getAsDouble();
        Logger.l("DefaultDrive: Power %f, Angle %f", _pow, _ang);
        drivebase.driveArcade(_pow, _ang);
    }
}