package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.team5431.titan.core.robot.Component;

/**
 * @author Ryan Hirasaki
 */
public class Balancer extends Component<Robot> {

    private WPI_VictorSPX balancer;

    public Balancer() {
        balancer = new WPI_VictorSPX(Constants.CLIMBER_BALANCER_ID);
        balancer.setInverted(Constants.CLIMBER_BALANCER_REVERSE);
    }

    @Override
    public void disabled(final Robot robot) {
    }

    @Override
    public void init(final Robot robot) {
    }

    @Override
    public void periodic(final Robot robot) {
    }

    public void setSpeed(double speed) {
        balancer.set(ControlMode.PercentOutput, speed);
    }
}