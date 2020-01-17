package frc.robot.components;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.robot.Component;

public class Drivebase extends Component<Robot> {

    private WPI_TalonFX left;
    private WPI_TalonFX right;

    private WPI_TalonFX _leftFollow;
    private WPI_TalonFX _rightFollow;

    public Drivebase() {
        left = new WPI_TalonFX(Constants.DRIVEBASE_LEFT_FRONT_ID);
        right = new WPI_TalonFX(Constants.DRIVEBASE_RIGHT_FRONT_ID);

        _leftFollow = new WPI_TalonFX(Constants.DRIVEBASE_LEFT_BACK_ID);
        _rightFollow = new WPI_TalonFX(Constants.DRIVEBASE_RIGHT_BACK_ID);

        _leftFollow.follow(left);
        _rightFollow.follow(right);

        left.setInverted(Constants.DRIVEBASE_LEFT_REVERSE);
        right.setInverted(Constants.DRIVEBASE_RIGHT_REVERSE);
    }

    @Override
    public void init(Robot robot) {
    }

    @Override
    public void periodic(Robot robot) {
    }

    @Override
    public void disabled(Robot robot) {
    }

    public void drive(double driveLeft, double driveRight) {
        left.set(driveLeft);
        right.set(driveRight);
    }
}