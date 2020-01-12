package frc.robot;

import java.util.List;

import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.TitanRobot;

public class Robot extends TitanRobot<Robot> {
  public static enum Mode{
    DISABLED, AUTO, TELEOP, TEST
  }

  private Mode mode = Mode.DISABLED;
  private List<Component<Robot>> components = List.of();

  @Override
  public void robotInit() {
    Logger.DEBUG = true;
  }


  // The Following is Initializer Functions

  @Override
  public void teleopInit() {
    mode = Mode.TELEOP;
    components.forEach((com)->com.init(this));
  }

  @Override
  public void autonomousInit() {
    mode = Mode.AUTO;
    components.forEach((com)->com.init(this));
  }

  @Override
  public void testInit(){
    mode = Mode.TEST;
    components.forEach((com)->com.init(this));
  }

  @Override
  public void disabledInit(){
    mode = Mode.DISABLED;
    components.forEach((com)->com.disabled(this));
  }


  // The Following is Periodic Functions

  @Override
  public void robotPeriodic() {
    components.forEach((com)->com.tick(this));
  }

  @Override
  public void teleopPeriodic() {
    components.forEach((com)->com.periodic(this));
  }

  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
  }

  @Override
  public void testPeriodic(){
    teleopPeriodic();
  }

  @Override
  public void disabledPeriodic() {
    // This Function Should Do Nothing
  }

  @Override
  public List<Component<Robot>> getComponents() {
    return components;
  }

  public Mode getMode() {
    return mode;
  }
}
