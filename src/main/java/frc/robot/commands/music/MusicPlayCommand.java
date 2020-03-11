package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;

public class MusicPlayCommand extends InstantCommand {
    public MusicPlayCommand(Music music) {
        super(music::play);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}