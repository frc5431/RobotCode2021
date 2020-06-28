package frc.robot.commands.music;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Music;

public class MusicStopCommand extends InstantCommand {
    public MusicStopCommand(Music music) {
        super(music::stop);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}