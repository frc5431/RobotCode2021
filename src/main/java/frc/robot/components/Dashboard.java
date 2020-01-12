package frc.robot.components;

import frc.robot.Robot;
import frc.robot.Robot.Mode;
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
    }

    @Override
    public void init(Robot robot) {
        SmartDashboard.putData("Drive Type", driveType);
    }

    @Override
    public void periodic(Robot robot) {
    }

    @Override
    public void disabled(Robot robot) {
    }

    @Override
    public void tick(Robot robot) {
        final Mode mode = robot.getMode();
        SmartDashboard.putString("Mode", mode.toString());
    }

    public DriveType getSelectedDriveType() {
        return driveType.getSelected();
    }
}