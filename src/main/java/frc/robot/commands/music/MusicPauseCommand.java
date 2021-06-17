package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;
import frc.team5431.titan.core.misc.Logger;

public class MusicPauseCommand extends InstantCommand {
    public MusicPauseCommand(Music music) {
        super(() -> { Logger.l("Music pause command exited with code %s", music.pause()); });
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}