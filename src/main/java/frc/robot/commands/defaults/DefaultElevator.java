package frc.robot.commands.defaults;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Systems;
import frc.robot.subsystems.Elevator;

/**
 * @author Ryan Hirasaki
 */
public class DefaultElevator extends CommandBase {

    private final Elevator elevator;
    private final DoubleSupplier pow;

    public DefaultElevator(Systems systems, DoubleSupplier power) {
        this.elevator = systems.getElevator();
        this.pow = power;

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setSpeed(pow.getAsDouble());
    }
}