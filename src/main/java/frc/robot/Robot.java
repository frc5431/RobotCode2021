package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team5431.titan.core.misc.Logger;

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

  private boolean performedEnabled = false;

  // The Following is Initializer Functions

  @Override
  public void robotInit() {
    Logger.DEBUG = true;
    robotMap = new RobotMap();
	CameraServer.getInstance().startAutomaticCapture();
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

    robotMap.resetBallCount();
  }

  @Override
  public void teleopInit() {
    if (autonCommand != null) {
      autonCommand.cancel();
    }

    // robotMap.resetEncoders();
    robotMap.resetBallCount();
  }

//   @Override
//   public void disabledPeriodic() {
// 	  robotMap.disabled();
//   }

  @Override
  public void autonomousPeriodic() {
    if (!performedEnabled) {
      robotMap.enabled();
      performedEnabled = true;
    }
  }

  @Override
  public void teleopPeriodic() {
    if (!performedEnabled) {
      robotMap.enabled();
      performedEnabled = true;
    }
  }
  
  @Override
  public void disabledInit() {
    robotMap.disabled();
    performedEnabled = false;
  }
}
