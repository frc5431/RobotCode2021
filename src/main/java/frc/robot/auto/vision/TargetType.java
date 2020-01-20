package frc.robot.auto.vision;

public enum TargetType {
    
   UPPERPORT(0),
   LOWERPORT(1),
   LOADINGBAY(2);

    private final int pipeline;

    private TargetType(final int pipe){
        this.pipeline = pipe;
    }

    public int getPipeline(){
        return pipeline;
    }

}