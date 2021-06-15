package frc.robot;

import edu.wpi.first.wpiutil.math.Pair;

/**
 * @author Ryan Hirasaki
 */
public class AutoConstants {

	public static final double kMaxSpeedMetersPerSecond = 0;
	public static final double kMaxAccelerationMetersPerSecondSquared = 0;
	public static final double kRamseteB = 0;
	public static double kRamseteZeta;

    public static final int SHOOTER_FLYWHEEL_VELOCITY_AUTO = 13600; // 15400

	// Pairs are speed, ms timeout
	public static final Pair<Double, Long> FORWARD_AUTO = Pair.of(-0.3, 500L);
	public static final Pair<Double, Long> BACKWARD_AUTO = Pair.of(0.3, 1250L);
	public static final double WAIT_TIMEOUT = 0.2; // in seconds 
}
