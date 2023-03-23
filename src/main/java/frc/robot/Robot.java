package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
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

  private boolean performedEnabled = false;

  // The Following is Initializer Functions

  @Override
  public void robotInit() {
    robotMap = new RobotMap();
    if (UsbCamera.enumerateUsbCameras().length > 0) {
      try {
        CameraServer.startAutomaticCapture();
      } catch (Exception e) {}
    }
    robotMap.printAutonChooser();
    robotMap.printDriveModeChooser();
    robotMap.disabled();
  }
  
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

    /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    autonCommand = robotMap.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (autonCommand != null) {
      autonCommand.schedule();
    }

    robotMap.resetBallCount();
    robotMap.printAutonChooser();
  }

  @Override
  public void teleopInit() {
    if (autonCommand != null) {
      autonCommand.cancel();
    }

    // robotMap.resetEncoders();
    robotMap.resetBallCount();
  }

  @Override
  public void disabledPeriodic() {
	  robotMap.disabledPeriodic();
  }

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
