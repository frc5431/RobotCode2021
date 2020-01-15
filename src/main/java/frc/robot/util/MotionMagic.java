package frc.robot.util;

public class MotionMagic {
    public final int kSlotId = 0;
    public final double kF;
    public final double kP;
    public final double kI;
    public final double kD;
    
    public final int kTimeoutMs = 30;
    public final int kPIDLoopIdx = 0;

    public MotionMagic(double f, double p, double i, double d) {
        kF = f;
        kP = p;
        kI = i;
        kD = d;
    }
}