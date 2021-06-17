package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;

public class MusicLoadCommand extends InstantCommand {
    public enum LoadType {
        OFFSET, ABSOLUTE, NAME
    }

    public static final MusicLoadCommand NEXT_SONG(Music music)     { return MusicLoadCommand.createMusicLoadCommand(music, LoadType.OFFSET, +1); }
    public static final MusicLoadCommand PREVIOUS_SONG(Music music) { return MusicLoadCommand.createMusicLoadCommand(music, LoadType.OFFSET, -1); }

    public static MusicLoadCommand createMusicLoadCommand(Music music, LoadType type, Object param) {
        if (type == LoadType.OFFSET)
            return new MusicLoadCommand(music, (int) param);
        else if (type == LoadType.ABSOLUTE)
            return new MusicLoadCommand(music, (int) param, 0); // Arbitrary number to differentiate from other one
        else
            return new MusicLoadCommand(music, (String) param);
    }

    private MusicLoadCommand(Music music, int offset) {
        super(() -> music.loadMusicSelectionOffset(offset));
    }

    private MusicLoadCommand(Music music, int index, int arbitrary) {
        super(() -> music.loadMusicSelectionAbs(index));
    }

    private MusicLoadCommand(Music music, String name) {
        super(() -> music.loadMusicSelectionName(name));
    }
}