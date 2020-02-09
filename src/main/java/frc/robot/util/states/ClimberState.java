package frc.robot.util.states;

import frc.team5431.titan.core.misc.Calc;

public enum ClimberState {
	// Add Correct Positions!
	TOP(15), BOTTOM(0);

	private double position;

	private ClimberState(double pos) {
		position = pos * 2048;
	}

	public double getPosition() {
		return position;
	}

	public static ClimberState getStateFromPosition(double position) {
		for (ClimberState state : ClimberState.values()) {
			// FIXME: Add Correct Range!
			if (Calc.approxEquals(position, state.getPosition(), 100))
				return state;
		}
		return null;
	}
}