package frc.robot.components;

import frc.robot.Robot;
import frc.robot.util.DriveType;
import frc.team5431.titan.core.robot.Component;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard extends Component<Robot> {

    private final SendableChooser<DriveType> driveType = new SendableChooser<>();

    public Dashboard() {

        /*
         * Add selection for which drive code to use as there are differences in
         * preference
         */
        driveType.setDefaultOption("Tank Drive", DriveType.TANK);
        driveType.addOption("Arcade Drive", DriveType.ARCADE);
        SmartDashboard.putData("Drive Type", driveType);
        SmartDashboard.putNumber("Shooter Speed", 0.50);
        SmartDashboard.putNumber("Feeder Speed", 0.50);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        // Setting Data from dashboard
        robot.getShooter().setShooterSpeed(getNumber("Shooter Speed",0.5));
        robot.getShooter().setFeedSpeed(getNumber("Feeder Speed",0.5));

        // Push data to dashboard
        putString("Mode", robot.getMode().toString());
    }

    @Override
    public void disabled(Robot robot) {
    }

    @Override
    public void tick(Robot robot) {
    }

    public DriveType getSelectedDriveType() {
        return driveType.getSelected();
    }

    public void putString(String key, String value) {
        SmartDashboard.putString(key, value);
    }

    public void putNumber(String key, double value) {
        SmartDashboard.putNumber(key, value);
    }

    public String getString(String key, String defaultStr) {
        return SmartDashboard.getString(key, defaultStr);
    }

    public double getNumber(String key, double defaultNum) {
        return SmartDashboard.getNumber(key, defaultNum);
    }
}