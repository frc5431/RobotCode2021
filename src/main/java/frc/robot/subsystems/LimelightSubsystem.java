package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team5431.titan.core.vision.LEDState;
import frc.team5431.titan.core.vision.Limelight;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Rishmita Rao
 */
public class LimelightSubsystem extends SubsystemBase {
    public static enum Positions{
        OFF(Constants.LIMELIGHT_PIPELINE_OFF, Constants.LIMELIGHT_PID_OFF),
        HALF(Constants.LIMELIGHT_PIPELINE_HALF, Constants.LIMELIGHT_PID_HALF),
        FULL(Constants.LIMELIGHT_PIPELINE_FULL, Constants.LIMELIGHT_PID_FULL);

        private final int pipeline; 
        private final PIDController turn;
        
        private Positions(int pipeline, PIDController turn){
            this.pipeline = pipeline; 
            this.turn = turn;
        }
        public int getPipeline()
        {
            return pipeline;
        }

        public PIDController getPIDControllerTurn()
        {
            return turn; 
        }
    }
}
