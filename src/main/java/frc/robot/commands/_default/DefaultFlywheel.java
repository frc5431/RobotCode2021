package frc.robot.commands._default;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Flywheel;

public class DefaultFlywheel extends CommandBase{

    private final Flywheel flywheel;
    private final DoubleSupplier pow;

    public DefaultFlywheel(Systems systems, DoubleSupplier power) {
        this.flywheel = systems.getFlywheel();
        this.pow = power;

        addRequirements(flywheel);
    }

    private double map(double v, double in_min, double in_max, double out_min, double out_max) {
        return (v - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    @Override
    public void execute() {
        double normalized = map(pow.getAsDouble(), -1.0, 1.0, 0.0, 1.0);
        flywheel.setSpeed(ControlMode.PercentOutput, normalized);
    }
}