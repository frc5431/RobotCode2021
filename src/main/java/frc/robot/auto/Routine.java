package frc.robot.auto;

public enum Routine {
    DO_NOTHING(null, null, null, null);

    private final Path startHatch, loadingStation, secondHatch;
    private final Sequence firstSequence, secondSequence;
    private final boolean swapped;

    private Routine(final Sequence sequence, final Path startHatch, final Path loadingStation, final Path secondHatch){
        this(sequence, startHatch, loadingStation, secondHatch, false);
    }

    private Routine(final Sequence sequence, final Path startHatch, final Path loadingStation, final Path secondHatch, final boolean swapped){
        this(sequence, startHatch, loadingStation, secondHatch, sequence, swapped);
    }

    private Routine(final Sequence firstSequence, final Path startHatch, final Path loadingStation, final Path secondHatch, final Sequence secondSequence){
        this(firstSequence, startHatch, loadingStation, secondHatch, secondSequence, false);
    }

    private Routine(final Sequence firstSequence, final Path startHatch, final Path loadingStation, final Path secondHatch, final Sequence secondSequence, final boolean swapped){
        this.firstSequence = firstSequence;
        this.secondSequence = secondSequence;
        this.startHatch = startHatch;
        this.loadingStation = loadingStation;
        this.secondHatch = secondHatch;
        this.swapped = swapped;
    }

    public Sequence getFirstSequence(){
        return firstSequence;
    }

    public Sequence getSecondSequence(){
        return secondSequence;
    }

    public Path getStartHatchPath(){
        return startHatch;
    }

    public Path getLoadingStationPath(){
        return loadingStation;
    }

    public Path getSecondHatchPath(){
        return secondHatch;
    }

    public boolean isSwapped(){
        return swapped;
    }
}