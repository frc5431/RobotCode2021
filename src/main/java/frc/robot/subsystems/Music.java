package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A subsystem that handles music. Should be run in perpetuity.
 * 
 * @author Colin Wong
 */
public class Music extends SubsystemBase {
    private Orchestra orchestra;
    private List<WPI_TalonFX> talons;
    private ErrorCode eCode;
    private boolean autoQueue = true;

    private String prevString = "";

    private enum MUSIC_STATE {
        PLAYING, PAUSED, STOPPED
    }

    private MUSIC_STATE state;
    private MUSIC_STATE prevChooserState;

    /* An array of songs that are available to be played, can you guess the song/artists? */
    private List<String> songs = new ArrayList<>(List.of(
        "song1.chrp",
        "song2.chrp",
        "song3.chrp",
        "song4.chrp",
        "song5.chrp",
        "song6.chrp",
        "song7.chrp",
        "song8.chrp",
        "song9.chrp", /* the remaining songs play better with three or more FXs */
        "song10.chrp",
        "song11.chrp"
    ));

    /* track which song is selected for play */
    int songSelection = 0;

    /* overlapped actions */
    int timeToPlayLoops = 0;

    SendableChooser<MUSIC_STATE> stateChooser = new SendableChooser<>();

    /**
     * Creates a Music subsystem with the base motors.
     * 
     * @param motors The motors to pass. Has a variable amount of motors.
     */
    public Music(WPI_TalonFX... motors) {
        this(List.of(motors));
    }

    /**
     * Creates a Music subsystem with the base motors.
     * 
     * @param motors The motors to pass in List form.
     */
    public Music(List<WPI_TalonFX> motors) {
        talons = motors;
        talons.forEach((motor) -> orchestra.addInstrument(motor));

        SmartDashboard.putString("Current Playing Song", "None");
        SmartDashboard.putString("Music State", state.toString());
        SmartDashboard.putString("Song Request", "");

        stateChooser.setDefaultOption("Stopped", MUSIC_STATE.STOPPED);
        stateChooser.addOption("Paused", MUSIC_STATE.PAUSED);
        stateChooser.addOption("Playing", MUSIC_STATE.PLAYING);
        SmartDashboard.putData("Music State Chooser", stateChooser);

        prevChooserState = stateChooser.getSelected();
    }

    @Override
    public void periodic() {
        /* if song selection changed, auto-play it */
        if (timeToPlayLoops > 0) {
            --timeToPlayLoops;
            if (timeToPlayLoops == 0) {
                /* scheduled play request */
                System.out.println("Auto-playing song.");
                orchestra.play();
            }
        }

        if (stateChooser.getSelected() != prevChooserState) {
            prevChooserState = stateChooser.getSelected();
            state = stateChooser.getSelected();
        }

        SmartDashboard.putString("Music State", state.toString());

        String songRequest = SmartDashboard.getString("Song Request", "");

        SmartDashboard.putString("Current Playing Song", getCurrentSong());
        if (songRequest != prevString) {
            prevString = songRequest;
            loadMusicSelectionName(songRequest);
        }
    }

    public MUSIC_STATE getState() {
        return state;
    }

    public Orchestra getOrchestra() {
        return orchestra;
    }

    public List<String> getSongs() {
        return songs;
    }

    public String getCurrentSong() {
        if (state != MUSIC_STATE.STOPPED)
            return songs.get(songSelection);
        else
            return "None";
    }

    public void setAutoQueue(boolean autoQueue) {
        this.autoQueue = autoQueue;
    }

    public ErrorCode play() {
        eCode = orchestra.play();
        state = MUSIC_STATE.PLAYING;
        return eCode;
    }

    public ErrorCode pause() {
        eCode = orchestra.pause();
        state = MUSIC_STATE.PAUSED;
        return eCode;
    }

    public ErrorCode stop() {
        eCode = orchestra.stop();
        state = MUSIC_STATE.STOPPED;
        return eCode;
    }

    /**
     * Loads music based on the given offset. Also auto-queues the selected song.
     * 
     * @param offset The number of songs to advance (or decrease) by
     */
    public void loadMusicSelectionOffset(int offset) {
        /* increment song selection */
        songSelection += offset;
        /* wrap song index in case it exceeds boundary */
        if (songSelection >= songs.size()) {
            songSelection = 0;
        }
        if (songSelection < 0) {
            songSelection = songs.size() - 1;
        }
        /* load the chirp file */
        orchestra.loadMusic(songs.get(songSelection));

        if (autoQueue)
            timeToPlayLoops = 10;
    }

    /**
     * Loads music based on the index of the song you want.
     * 
     * @param selection The index of the song
     */
    public void loadMusicSelectionAbs(int selection) {
        songSelection = selection;

        orchestra.loadMusic(songs.get(songSelection));

        if (autoQueue)
            timeToPlayLoops = 10;
    }

    /**
     * Loads music based on the file name. 
     * 
     * @param name The name of the file to play
     * @return Whether the program ran successfully and found a song.
     */
    public boolean loadMusicSelectionName(String name) {
        int index = songs.indexOf(name);

        if (index == -1)
            return false;
        
        songSelection = index;
        orchestra.loadMusic(songs.get(songSelection));

        if (autoQueue)
            timeToPlayLoops = 10;
        
        return true;
    }
}
