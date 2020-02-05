package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.*;
import frc.team5431.titan.core.robot.Component;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putNumber;
import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putString;
import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putData;
import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.getNumber;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * @author Ryan Hirasaki
 * @author Daniel Brubaker
 * @author Colin Wong
 */
public class Dashboard extends Component<Robot> {

    private final SendableChooser<DriveTypeSelector> driveType = new SendableChooser<>();

    public Dashboard() {

        /*
         * Add selection for which drive code to use as there are differences in
         * preference
         */
        driveType.setDefaultOption("Tank Drive", DriveTypeSelector.ARCADE);
        driveType.addOption("Arcade Drive", DriveTypeSelector.ARCADE);
        putData("Drive Type", driveType);
        putNumber("Shooter Speed", Constants.SHOOTER_FLYWHEEL_DEFAULT_SPEED);
        putNumber("Shooter RPM", 0.0);
        putNumber("Shooter Guessed Speed", 0.0);
        putNumber("Feeder Speed", Constants.SHOOTER_FEEDER_DEFAULT_SPEED);
        putNumber("Elevator Position", 0.0);
        putNumber("Drivebase Ramping", 0.5);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        // Setting Data from dashboard
        robot.getFlywheel().setShooterSpeed(getNumber("Shooter Speed", Constants.SHOOTER_FLYWHEEL_DEFAULT_SPEED));
        robot.getFeeder().setFeedSpeed(getNumber("Feeder Speed", Constants.SHOOTER_FEEDER_DEFAULT_SPEED));
        robot.getDrivebase().setRamping(getNumber("Drivebase Ramping", 0.5));

        // Push data to dashboard
        putString("Mode", robot.getMode().toString());
        putString("Driver Swapped", robot.getTeleop().getSwappedDriverStatus() ? "Swapped Drive" : "Regular Drive");
        putNumber("Drivebase Heading", robot.getDrivebase().getHeading());
        putNumber("Elevator Position", robot.getElevator().getRotations());
        putNumber("Shooter RPM", robot.getFlywheel().getFlywheelVelocity());
        putNumber("Shooter Guessed Speed", robot.getFlywheel().getFlywheelSpeed());
    }

    @Override
    public void disabled(Robot robot) {
    }

    @Override
    public void tick(Robot robot) {
    }

    public DriveTypeSelector getSelectedDriveType() {
        return driveType.getSelected();
    }
}