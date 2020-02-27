package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

public class PushBallsUpSubCommand extends ParallelCommandGroup {
    public PushBallsUpSubCommand(Intake intake, Hopper hopper, Feeder feeder) {
        addCommands(
            new FeederCommand(feeder, false),
            new HopperCommand(hopper, false),
            new IntakeCommand(intake, false)
        );
    }
}