package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;

public class MusicPauseCommand extends InstantCommand {
    public MusicPauseCommand(Music music) {
        super(music::pause);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}