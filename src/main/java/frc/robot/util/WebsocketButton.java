package frc.robot.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class WebsocketButton extends JoystickButton {

    private static final GenericHID m_joystick = new GenericHID(-1);
    private boolean buttonState = false;

    WebsocketButton() {
        super(m_joystick, 0);
    }

    public void set(boolean newValue){
        buttonState = newValue;
    }

    public boolean get(){
        return buttonState;
    }

}
