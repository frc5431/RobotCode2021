package frc.robot.util;

import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;

public class FalconUtils {
    public static int distanceToNativeUnits(double positionMeters) {
        double wheelRotations = positionMeters / (2 * Math.PI * Constants.ROBOT_DRIVEBASE_WHEEL_RADIUS);
        double motorRotations = wheelRotations * Constants.ROBOT_DRIVEBASE_GEAR_RATIO;
        int sensorCounts = (int) (motorRotations * Constants.ROBOT_DRIVEBASE_TPR);
        return sensorCounts;
    }

    public static int velocityToNativeUnits(double velocityMetersPerSecond) {
        double wheelRotationsPerSecond = velocityMetersPerSecond
                / (2 * Math.PI * Constants.ROBOT_DRIVEBASE_WHEEL_RADIUS);
        double motorRotationsPerSecond = wheelRotationsPerSecond * Constants.ROBOT_DRIVEBASE_GEAR_RATIO;
        double motorRotationsPer100ms = motorRotationsPerSecond / 10.0;
        int sensorCountsPer100ms = (int) (motorRotationsPer100ms * Constants.ROBOT_DRIVEBASE_TPR);
        return sensorCountsPer100ms;
    }

    public static double nativeUnitsToDistanceMeters(double sensorCounts) {
        double motorRotations = (double) sensorCounts / Constants.ROBOT_DRIVEBASE_TPR;
        double wheelRotations = motorRotations / Constants.ROBOT_DRIVEBASE_GEAR_RATIO;
        double positionMeters = wheelRotations * (2 * Math.PI * Constants.ROBOT_DRIVEBASE_WHEEL_RADIUS);
        return positionMeters;
    }
}
