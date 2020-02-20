package frc.robot.auto.vision;

public enum TargetType {
    
    SHIELDGENERATOR(0),
    LOADINGBAY(1);

    private final int pipeline;

    private TargetType(final int pipe){
        this.pipeline = pipe;
    }

    public int getPipeline(){
        return pipeline;
    }

}