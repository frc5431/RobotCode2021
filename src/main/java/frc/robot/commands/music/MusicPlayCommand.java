package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;
import frc.team5431.titan.core.misc.Logger;

public class MusicPlayCommand extends InstantCommand {
    public MusicPlayCommand(Music music) {
        super(() -> { Logger.l("Music play command exited with code %s", music.play()); });
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}