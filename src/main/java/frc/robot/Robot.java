package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * @author Ryan Hirasaki
 * @author Colin Wong
 * @author Rishmita Rao
 * @author Daniel Brubaker
 */
public class Robot extends TimedRobot {

    // Objects for mostly internal Robot.java usage
    private RobotMap robotMap = new RobotMap();
    private Command autonCommand;

    @Override
    public void robotInit() {
        robotMap.disabled();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        robotMap.resetBallCount();
        autonCommand = robotMap.getAutonCommand();
        if (autonCommand != null) {
            autonCommand.schedule();
        }
    }

    @Override
    public void teleopInit() {
        autonCommand.cancel();
        robotMap.resetBallCount();
    }
}
