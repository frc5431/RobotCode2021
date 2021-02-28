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
  private RobotMap robotMap;
  private Command autonCommand;

  // The Following is Initializer Functions

  @Override
  public void robotInit() {
    robotMap = new RobotMap();
	// CameraServer.getInstance().startAutomaticCapture();
	robotMap.outData();
	robotMap.disabled();

  }
  
  @Override
  public void robotPeriodic() {
	robotMap.outData();
    CommandScheduler.getInstance().run();
  }

    /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    robotMap.resetBallCount();
    autonCommand = robotMap.getAutonomousCommand();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonCommand != null) {
      autonCommand.schedule();
    }

  }

  @Override
  public void teleopInit() {
    // robotMap.resetEncoders();
    robotMap.resetBallCount();
    if (autonCommand != null) {
      autonCommand.cancel();
    }

  }

//   @Override
//   public void disabledPeriodic() {
// 	  robotMap.disabled();
//   }
}
