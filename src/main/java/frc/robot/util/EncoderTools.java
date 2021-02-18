package frc.robot.util;

import frc.robot.Constants;

public class EncoderTools {
    public static double ticksToAxleRevolutions(double ticks) {
        return ticks / Constants.COUNTS_PER_REVOLUTION;
    }
    
    public static double ticksToWheelRevolutions(double ticks) {
        return ticksToAxleRevolutions(ticks) * Constants.GEAR_RATIO;
    }
    
    public static double ticksToMeters(double ticks) {
        return ticksToWheelRevolutions(ticks) * Constants.WHEEL_CIRCUMFERENCE;
    }

    public static double axleRevolutionsToTicks(double revolutions) {
        return revolutions * Constants.COUNTS_PER_REVOLUTION;
    }
    
    public static double wheelRevolutionsToTicks(double revolutions) {
        return axleRevolutionsToTicks(revolutions / Constants.GEAR_RATIO);
    }

    public static double metersToTicks(double meters) {
        return wheelRevolutionsToTicks(meters / Constants.WHEEL_CIRCUMFERENCE);
    }
}
