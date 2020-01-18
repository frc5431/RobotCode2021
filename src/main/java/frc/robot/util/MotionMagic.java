package frc.robot.util;

public class MotionMagic {
    public final double kF;
    public final double kP;
    public final double kI;
    public final double kD;
    public final int kIntegralZone;
    public final double kPeakOutput;
    public final int kClosedLoopTime;

    public MotionMagic(double p, double i, double d, double f, int integralZone, double peakOutput, int closedLoopTime) {
        kF = f;
        kP = p;
        kI = i;
        kD = d;
        kIntegralZone = integralZone;
        kPeakOutput = peakOutput;
        kClosedLoopTime = closedLoopTime;
    }
}