package frc.robot.util;

import frc.robot.Constants;

public class EncoderTools {
    public static double ticksToMeters(double ticks) {
        double wheel_rotation = (ticksToRevolutions(ticks)) * Constants.GEAR_RATIO;
        return wheel_rotation * Constants.WHEEL_CIRCUMFERENCE;
    }

    public static double ticksToRevolutions(double ticks) {
        return ticks / Constants.COUNTS_PER_REVOLUTION;
    }

    public static double revolutionsToTicks(double revolutions) {
        return revolutions * Constants.COUNTS_PER_REVOLUTION;
    }

    public static double metersToTicks(double meters) {
        double wheel_rotations =  meters / Constants.WHEEL_CIRCUMFERENCE;
        return revolutionsToTicks(wheel_rotations / Constants.GEAR_RATIO);
    }
}
