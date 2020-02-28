package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class DefaultElevator extends CommandBase {

    private final Elevator elevator;
    private final DoubleSupplier pow;

    public DefaultElevator(Elevator elevator, DoubleSupplier power) {
        this.elevator = elevator;
        this.pow = power;

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setSpeed(pow.getAsDouble());
    }
}