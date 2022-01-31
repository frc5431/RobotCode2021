package frc.robot.util;

import javax.sound.midi.MidiUnavailableException;

import org.rjung.util.launchpad.Channel;
import org.rjung.util.launchpad.Launchpad;
import org.rjung.util.launchpad.LaunchpadReceiver;
import org.rjung.util.launchpad.Pad;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * @author Colin Wong
 */
public class LaunchpadHID extends GenericHID {

    private Launchpad launchpad;

    public LaunchpadHID(int port, Channel midiChannel) {
        super(port);

        try {
            launchpad = new Launchpad(midiChannel, new LaunchpadReceiver() {

                @Override
                public void receive(Pad pad) {
                    // TODO Auto-generated method stub
                    
                }
                
            });
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

}
