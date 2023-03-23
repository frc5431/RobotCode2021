package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WrapperCommand;
import frc.robot.Constants;
import frc.robot.Systems;
import frc.robot.commands.subsystems.FeederCommand;
import frc.robot.commands.subsystems.FlywheelCommand;
import frc.robot.commands.subsystems.HopperCommand;
import frc.robot.commands.subsystems.IntakeCommand;
import frc.robot.commands.subsystems.PivotCommand;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Flywheel.Velocity;
import frc.robot.util.ShootPosition;
import frc.team5431.titan.core.misc.Logger;

import static edu.wpi.first.wpilibj2.command.Commands.*;

/**
 * Class to store command group factories
 * 
 * Needed for 2023 compatability
 * 
 * @author Colin Wong
 */
public class CommandGroups {

    public static CommandBase FloorIntakeCommand(Systems systems) {
        return parallel(
            new IntakeCommand(systems, false),
			new HopperCommand(systems, false),
			new PivotCommand(systems, Pivot.POSITION.DOWN)
        );
    }

    public static CommandBase HumanPlayerIntake(Systems systems) {
        return new HopperCommand(systems, false).until(
            () -> (systems.getFeeder().getBallCount() >= 3)
        );
    }

    public static CommandBase ShootSuperCommand(Systems systems, ShootPosition pos, boolean rpmWait) {
        return parallel(
            sequence(
				waitSeconds(Constants.SHOOTER_FLYWHEEL_COMMAND_WAIT),
                new FlywheelCommand(systems, switch (pos) {
                    case CLOSE -> Flywheel.Velocity.HALF;
                    case FAR -> Flywheel.Velocity.FULL;
                    default -> Flywheel.Velocity.AUTON;
                })
            ),
            sequence(
                runOnce(() -> {Feeder.ENABLE_AUTO_FEEDER = false;}), // Disable Auto Indexer
                new PushBallDownCommand(systems),
                new WaitTillFlywheelAtSpeed(systems, rpmWait),
                PushBallsUpSubCommand(systems, pos, rpmWait),
                runOnce(() -> {Feeder.ENABLE_AUTO_FEEDER = true;}), // Enable Auto Indexer
                runOnce(() -> {FlywheelCommand.KILL = true;})
            )
        ).finallyDo((interrupted) -> {
            systems.getFeeder().resetVars();
            systems.getFeeder().set(0);
            systems.getFlywheel().set(Velocity.OFF);
            systems.getHopper().set(0, 0);
            Feeder.ENABLE_AUTO_FEEDER = true;
            FlywheelCommand.KILL = false;
            Logger.l("Shooter Super Command Finished!");
        });
    }

    public static CommandBase StowSuperCommand(Systems systems) {
        return parallel(
            new PivotCommand(systems, Pivot.POSITION.UP, false), // Brings the Intake Up
			new FlywheelCommand(systems, Flywheel.Velocity.OFF), // Stop The Flywheel
			new HopperCommand(systems, 0.0, 0.0), // Stop The Hopper
			new FeederCommand(systems, 0.0, false), // Stop The Feeder
			new IntakeCommand(systems, 0.0) // Stop The Intake
        );
    }

    public static CommandBase PushBallsUpSubCommand(Systems systems, ShootPosition pos, boolean rpmWait) {
        return new WrapperCommand(parallel(
            new HopperCommand(systems, false),
			new FeederCommand(systems, switch (pos) {
                case CLOSE -> Constants.SHOOTER_FEEDER_DEFAULT_SPEED;
                case FAR -> Constants.SHOOTER_FEEDER_FAR_DEFAULT_SPEED;
                default -> Constants.SHOOTER_FEEDER_AUTON_DEFAULT_SPEED;
            }, true, rpmWait)
        )) {
            long lastBallCountedTime = 0;

            @Override
            public void initialize() {
                super.initialize();
                lastBallCountedTime = System.currentTimeMillis(); 
            }

            @Override
            public void execute() {
                super.execute();
                if(!systems.getFeeder().isEmpty()){
                    lastBallCountedTime = System.currentTimeMillis();
                }
            }

            @Override
            public boolean isFinished() {
                return lastBallCountedTime + 500 < System.currentTimeMillis();
            }
        };
    }

}
