package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Systems;
import frc.robot.commands.subsystems.PivotCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;

/**
 * @author Ryan Hirasaki
 */
public class DefaultElevator extends CommandBase {
    private final Elevator elevator;
    private final Systems systems;
    private final DoubleSupplier pow;

    public DefaultElevator(Systems systems, DoubleSupplier power) {
        this.systems = systems;
        this.elevator = systems.getElevator();
        this.pow = power;

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setSpeed(pow.getAsDouble());
        if (pow.getAsDouble() > 0) CommandScheduler.getInstance().schedule(new PivotCommand(systems, Pivot.POSITION.DOWN));
    }
}