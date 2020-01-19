package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Robot.Mode;
import frc.team5431.titan.core.joysticks.LogitechExtreme3D;
import frc.team5431.titan.core.joysticks.Xbox;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.robot.Component;

public class Teleop extends Component<Robot> {

    private Xbox driver;
    private LogitechExtreme3D operator;

    private boolean warnDriver = false, warnOperator = false;

    public Teleop() {
        driver = new Xbox(Constants.DRIVER_XBOX_ID);
        driver.setDeadzone(Constants.DRIVER_XBOX_DEADZONE);

        operator = new LogitechExtreme3D(Constants.OPERATOR_LOGITECH_ID);
        operator.setDeadzone(Constants.OPERATOR_LOGITECH_DEADZONE);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
        // Do not run if not in Teleop mode
        if (robot.getMode() != Mode.TELEOP)
            return;

        // Run the respective controller code!
        driver(robot);
        operator(robot);
    }

    @Override
    public void disabled(Robot robot) {
    }

    private void driver(Robot robot) {
        final Drivebase drivebase = robot.getDrivebase();
        final Dashboard dashboard = robot.getDashboard();

        final String driverName = driver.getName().toLowerCase();

        if (driverName.contains(Constants.DRIVER_XBOX_NAME.toLowerCase())) {
            double left;
            double right;

            /*
             * This code will allow the end user to chose which drive controls to use as
             * there is a difference in preference.
             */
            switch (dashboard.getSelectedDriveType()) {
            case ARCADE:
                left = -driver.getRawAxis(Xbox.Axis.LEFT_Y) + driver.getRawAxis(Xbox.Axis.LEFT_X) * .5;
                right = -driver.getRawAxis(Xbox.Axis.LEFT_Y) - driver.getRawAxis(Xbox.Axis.LEFT_X) * .5;
                break;
            case TANK:
                left = driver.getRawAxis(Xbox.Axis.LEFT_Y) * -1;
                right = driver.getRawAxis(Xbox.Axis.RIGHT_Y) * -1;
                break;
            default:
                left = 0;
                right = 0;
                break;
            }

            drivebase.drivePercentage(left, right);

            robot.getIntake().getToggle().isToggled(driver.getRawButton(Xbox.Button.A));
            robot.getShooter().getFeedToggle().isToggled(driver.getRawButton(Xbox.Button.A));

            robot.getShooter().getFlywheelToggle().setState(driver.getRawButton(Xbox.Button.B));
        }
        else {
            if(!warnDriver)
                Logger.e("Driver Controller Not Connected");
            warnDriver = true;
        }
    }

    private void operator(Robot robot) {
        final String operatorName  = operator.getName().toLowerCase();

        if (operatorName.contains(Constants.OPERATOR_LOGITECH_NAME.toLowerCase())) {

        } else {
            if(!warnOperator)
                Logger.e("Operator Controller Not Connected");
            warnOperator = true;
        }
    }
}