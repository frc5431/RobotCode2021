package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Ryan Hirasaki
 */
public class Balancer extends SubsystemBase {

    private WPI_TalonSRX balancer;

    public Balancer(WPI_TalonSRX balancer) {
        this.balancer = balancer;
        this.balancer.setInverted(Constants.CLIMBER_BALANCER_REVERSE);

    }

    public void set(double speed) {
        balancer.set(ControlMode.PercentOutput, speed);
    }
}