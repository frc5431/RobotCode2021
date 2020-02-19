package frc.robot.components;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Robot.Mode;
import frc.team5431.titan.core.joysticks.LogitechExtreme3D;
import frc.team5431.titan.core.joysticks.Xbox;
import frc.team5431.titan.core.misc.Logger;
import frc.team5431.titan.core.misc.Toggle;
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
            double left = 0;
            double right = 0;

            double power = 0;
            double turn = 0;

            /*
             * This code will allow the end user to chose which drive controls to use 
             * from the smart dashboard as there is a difference in preference.
             */
            switch (dashboard.getSelectedDriveType()) {
                case ARCADE:
                    power = driver.getRawAxis(Xbox.Axis.LEFT_Y) * -1; // Set negative as xbox                                                                                  // foward is negative
                    turn = driver.getRawAxis(Xbox.Axis.LEFT_X) * -1; // Set negative as right is negative

                    drivebase.drivePercentageArcade(power, turn);
                    break;
                case TANK:
                    left = driver.getRawAxis(Xbox.Axis.LEFT_Y) * -1;
                    right = driver.getRawAxis(Xbox.Axis.RIGHT_Y) * -1;

                    drivebase.drivePercentageTank(left, right);
                    break;
                default:
                    break;
            }

            robot.getIntake().getToggle().setState(driver.getRawButton(Xbox.Button.X));
            robot.getFeeder().getFeedToggle().setState(driver.getRawButton(Xbox.Button.A));

            robot.getFlywheel().getFlywheelToggle().setState(driver.getRawButton(Xbox.Button.B));

            robot.getElevator().setSpeed(driver.getRawAxis(Xbox.Axis.TRIGGER_RIGHT) - driver.getRawAxis(Xbox.Axis.TRIGGER_LEFT));

            robot.getIntake().getReverse().isToggled(driver.getRawButton(Xbox.Button.Y));
            robot.getFeeder().getReverse().isToggled(driver.getRawButton(Xbox.Button.Y));

            robot.getVision().getTargetToggle().isToggled(driver.getRawButton(Xbox.Button.BUMPER_R));
            robot.getVision().getVisionLightToggle().isToggled(driver.getRawButton(Xbox.Button.BUMPER_L));
        } else {
            if (!warnDriver)
                Logger.e("Driver Controller Not Connected");
            warnDriver = true;
        }
    }

    private void operator(Robot robot) {
        final String operatorName = operator.getName().toLowerCase();

        if (operatorName.contains(Constants.OPERATOR_LOGITECH_NAME.toLowerCase())) {

            double elevatorSpeed = operator.getRawAxis(LogitechExtreme3D.Axis.SLIDER);
            double balancerSpeed = operator.getRawAxis(LogitechExtreme3D.Axis.X);

            robot.getElevator().setSpeed(elevatorSpeed);
            robot.getBalancer().setSpeed(balancerSpeed);

        } else {
            if (!warnOperator)
                Logger.e("Operator Controller Not Connected");
            warnOperator = true;
        }
    }


	public Xbox getDriver() {
		return driver;
	}
}