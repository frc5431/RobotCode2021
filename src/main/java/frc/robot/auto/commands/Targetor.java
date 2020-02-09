package frc.robot.auto.commands;

import frc.robot.Robot;
import frc.robot.auto.vision.TargetType;
import frc.team5431.titan.core.robot.Command;
import frc.team5431.titan.core.vision.Limelight;

public class Targetor extends Command<Robot> {

    private Limelight front;
    private final TargetType target;

    public Targetor(TargetType target) {
        this.target = target;

        this.name = "Targetor";
    }

    @Override
    public void init(Robot robot) {
        front = robot.getVision().getFrontLimelight();
    }

    // FIXME: Do Proper Targeting
    @Override
    public CommandResult update(Robot robot) {
        return null;
    }

    @Override
    public void done(Robot robot) {
    }
}