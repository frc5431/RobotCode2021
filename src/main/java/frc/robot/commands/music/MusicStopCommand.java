package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;
import frc.team5431.titan.core.misc.Logger;

public class MusicStopCommand extends InstantCommand {
    public MusicStopCommand(Music music) {
        super(() -> { Logger.l("Music stop command exited with code %s", music.stop()); });
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}