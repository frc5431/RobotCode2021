package frc.robot.util;

import frc.robot.Constants;

public class EncoderTools {
    public static double ticksToMeters(double ticks) {
        double wheel_rotation = (ticksToAxleRevolutions(ticks)) * Constants.GEAR_RATIO;
        return wheel_rotation * Constants.WHEEL_CIRCUMFERENCE;
    }

    public static double ticksToAxleRevolutions(double ticks) {
        return ticks / Constants.COUNTS_PER_REVOLUTION;
    }

    public static double axleRevolutionsToTicks(double revolutions) {
        return revolutions * Constants.COUNTS_PER_REVOLUTION;
    }

    public static double metersToTicks(double meters) {
        double wheel_rotations =  meters / Constants.WHEEL_CIRCUMFERENCE;
        return axleRevolutionsToTicks(wheel_rotations / Constants.GEAR_RATIO);
    }
}
