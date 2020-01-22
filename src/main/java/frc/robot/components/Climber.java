package frc.robot.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.util.ClimberState;
import frc.team5431.titan.core.robot.Component;

public class Climber extends Component<Robot> {

	private WPI_TalonFX motor;

	@Override
	public void init(Robot robot) {
		motor = new WPI_TalonFX(Constants.CLIMBER_ELEVATOR_ID);
		motor.setInverted(Constants.CLIMBER_ELEVATOR_REVERSE);
		motor.setNeutralMode(NeutralMode.Brake);

		motor.getSensorCollection().setIntegratedSensorPosition(0, 15);
		motor.getSensorCollection().setIntegratedSensorPosition(0, 15);
	}

	@Override
	public void periodic(Robot robot) {
	}

	@Override
	public void disabled(Robot robot) {
	}

	public void setPositionState(ClimberState state) {
		motor.set(ControlMode.Position, state.getPosition());
	}

	public void setSpeed(double value) {
		motor.set(ControlMode.PercentOutput, value);
	}

	public double getRotations() {
		return motor.getSensorCollection().getIntegratedSensorPosition() / 2048.0;
	}
}