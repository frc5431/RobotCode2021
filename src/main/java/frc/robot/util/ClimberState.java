package frc.robot.util;

public enum ClimberState {
	TOP(15), BOTTOM(0);

	private double position;

	private ClimberState(double pos) {
		position = pos * 2048;
	}

	public double getPosition() {
		return position;
	}
}