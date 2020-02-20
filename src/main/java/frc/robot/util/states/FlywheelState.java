package frc.robot.util.states;

import frc.team5431.titan.core.misc.Calc;

public enum FlywheelState {
    // FIXME: Add Correct values!
    STOP(0), HALF(50), FULL(100);

    private int velocity;
    private FlywheelState(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }

    public static FlywheelState getStateFromPosition(double position) {
		for (FlywheelState state : FlywheelState.values()) {
            // FIXME: Add Correct Range!
			if (Calc.approxEquals(position, state.getVelocity(), 100))
				return state;
		}
		return null;
	}
}