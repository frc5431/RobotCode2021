package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
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

    private static enum Path {

    };

    SendableChooser<Path> chooser = new SendableChooser<>();

    public RobotMap() {

        bindKeys();

        drivebase.setDefaultCommand(new DefaultDrive(drivebase, () -> driver.getRawAxis(Xbox.Axis.LEFT_Y),
                () -> driver.getRawAxis(Xbox.Axis.LEFT_X)));
    }

    private void bindKeys() {
        {
            new JoystickButton(driver, Xbox.Button.BUMPER_R.ordinal() + 1)
                    .toggleWhenPressed(new FlywheelControl(flywheel, Flywheel.Speeds.FULL), true);
            new JoystickButton(driver, Xbox.Button.X.ordinal() + 1).whenPressed(new BalancerCommand(balancer, true));
            new JoystickButton(driver, Xbox.Button.X.ordinal() + 1).whenPressed(new BalancerCommand(balancer, false));
        }

        {
            new JoystickButton(operator, LogitechExtreme3D.Button.ELEVEN.ordinal() + 1)
                    .whenPressed(new Targetor(drivebase, limelight));
        }
    }

    public Command getAutonomousCommand() {

        final var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics, 10);

        final TrajectoryConfig config = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(DriveConstants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);

        Trajectory trajectory = null;

        switch (chooser.getSelected()) {
        default:
            trajectory = TrajectoryGenerator.generateTrajectory(
                    // Start at the origin facing the +X direction
                    new Pose2d(0, 0, new Rotation2d(0)),
                    // Pass through these two interior waypoints, making an 's' curve path
                    List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                    // End 3 meters straight ahead of where we started, facing forward
                    new Pose2d(3, 0, new Rotation2d(0)),
                    // Pass config
                    config);
        }

        assert (trajectory != null);

        final RamseteCommand ramseteCommand = new RamseteCommand(trajectory, drivebase::getPose,
                new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics, drivebase::getWheelSpeeds,
                new PIDController(DriveConstants.kPDriveVel, 0, 0), new PIDController(DriveConstants.kPDriveVel, 0, 0),
                // RamseteCommand passes volts to the callback
                drivebase::tankDriveVolts, drivebase);

        return ramseteCommand.andThen(() -> drivebase.tankDriveVolts(0, 0));

        // return chooser.getSelected();
    }
}