package frc.robot;

import java.util.List;

import frc.robot.components.*;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.TitanRobot;

public class Robot extends TitanRobot<Robot> {
  public static enum Mode {
    DISABLED, AUTO, TELEOP, TEST
  }

  // Component Objects
  private Dashboard dashboard;
  private Drivebase drivebase;
  private Teleop teleop;
  private Intake intake;
  private Flywheel flywheel;
  private Balancer balancer;
  private Elevator elevator;
  private Feeder feeder;
  private Vision vision;
  private Auton auton;

  // Objects for mostly internal Robot.java usage
  private Mode mode = Mode.DISABLED;
  private List<Component<Robot>> components = List.of();

  // The Following is Initializer Functions

  @Override
  public void robotInit() {
    Logger.DEBUG = true;

    // Initialize Components
    dashboard = new Dashboard();
    drivebase = new Drivebase();
    teleop = new Teleop();
    intake = new Intake();
    flywheel = new Flywheel();
    feeder = new Feeder();
    balancer = new Balancer();
    elevator = new Elevator();
    vision = new Vision();
    auton = new Auton();

    // Add Components to components Array
    components = List.of(dashboard, drivebase, teleop, intake, flywheel, balancer, feeder, vision, auton);
  }

  @Override
  public void teleopInit() {
    mode = Mode.TELEOP;
    components.forEach((com) -> com.init(this));
  }

  @Override
  public void autonomousInit() {
    mode = Mode.AUTO;
    components.forEach((com) -> com.init(this));
  }

  @Override
  public void testInit() {
    mode = Mode.TEST;
    components.forEach((com) -> com.init(this));
  }

  @Override
  public void disabledInit() {
    mode = Mode.DISABLED;
    components.forEach((com) -> com.disabled(this));
  }

  // The Following is Periodic Functions

  @Override
  public void robotPeriodic() {
    components.forEach((com) -> com.tick(this));
  }

  @Override
  public void teleopPeriodic() {
    components.forEach((com) -> com.periodic(this));
  }

  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
  }

  @Override
  public void testPeriodic() {
    teleopPeriodic();
  }

  @Override
  public void disabledPeriodic() {
    teleopPeriodic();
  }

  /**
   * @return the components
   */
  @Override
  public List<Component<Robot>> getComponents() {
    return components;
  }

  /**
   * @return the mode
   */
  public Mode getMode() {
    return mode;
  }

  /**
   * @return the drivebase
   */
  public Drivebase getDrivebase() {
    return drivebase;
  }

  /**
   * @return the dashboard
   */
  public Dashboard getDashboard() {
    return dashboard;
  }

  /**
   * @return the teleop
   */
  public Teleop getTeleop() {
    return teleop;
  }

  /**
   * @return the intake
   */
  public Intake getIntake() {
    return intake;
  }

  /**
   * @return the flywheel
   */
  public Flywheel getFlywheel() {
    return flywheel;
  }

  /**
   * @return the feeder
   */
  public Feeder getFeeder() {
    return feeder;
  }

  /**
   * @return the vision
   */
  public Vision getVision() {
    return vision;
  }

  /**
   * @return the elevator
   */
  public Elevator getElevator() {
    return elevator;
  }

  /**
   * @return the balancer
   */
  public Balancer getBalancer() {
    return balancer;
  }

  /**
   * @return the auton
   */
  public Auton getAuton() {
    return auton;
  }
}
