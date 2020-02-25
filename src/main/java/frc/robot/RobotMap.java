package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.BalancerCommand;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.FlywheelControl;
import frc.robot.commands.Targetor;
import frc.robot.subsystems.*;
import frc.team5431.titan.core.joysticks.LogitechExtreme3D;
import frc.team5431.titan.core.joysticks.Xbox;
import frc.team5431.titan.core.vision.Limelight;

public class RobotMap {
    private final Balancer balancer = new Balancer();
    private final Drivebase drivebase = new Drivebase();
    private final Elevator elevator = new Elevator();
    private final Feeder feeder = new Feeder();
    private final Flywheel flywheel = new Flywheel();
    private final Hopper hopper = new Hopper();
    private final Intake intake = new Intake();

    private final Xbox driver = new Xbox(0);
    private final LogitechExtreme3D operator = new LogitechExtreme3D(1);

    private final Limelight limelight = new Limelight(Constants.VISION_FRONT_LIMELIGHT);

    SendableChooser<Command> chooser = new SendableChooser<>();

    public RobotMap() {

        bindKeys();

        drivebase.setDefaultCommand(new DefaultDrive(drivebase, () -> driver.getRawAxis(Xbox.Axis.LEFT_Y),
                () -> driver.getRawAxis(Xbox.Axis.LEFT_X)));
    }

    private void bindKeys() {
        {
            new JoystickButton(driver, Xbox.Button.BUMPER_R.ordinal()+1).toggleWhenPressed(new FlywheelControl(flywheel, Flywheel.Speeds.FULL), true);
            new JoystickButton(driver, Xbox.Button.X.ordinal()+1).whenPressed(new BalancerCommand(balancer, true));
            new JoystickButton(driver, Xbox.Button.X.ordinal()+1).whenPressed(new BalancerCommand(balancer, false));
        }

        {
            new JoystickButton(operator, LogitechExtreme3D.Button.ELEVEN.ordinal()+1).whenPressed(new Targetor(drivebase, limelight));
        }
    }

    public Command getAutonomousCommand() {
        return chooser.getSelected();
    }
}