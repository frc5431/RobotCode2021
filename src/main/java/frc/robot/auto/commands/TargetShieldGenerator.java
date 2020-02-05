package frc.robot.auto.commands;

import frc.robot.Robot;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.vision.Limelight;

public class TargetShieldGenerator extends Command<Robot> {

    Limelight front;

    public TargetShieldGenerator() {
        this.name = "TargetShieldGenerator";
    }

    @Override
    public void init(Robot robot) {
        front = robot.getVision().getFrontLimelight();
    }

    @Override
    public CommandResult update(Robot robot) {
        return null;
    }

    @Override
    public void done(Robot robot) {
    }
}