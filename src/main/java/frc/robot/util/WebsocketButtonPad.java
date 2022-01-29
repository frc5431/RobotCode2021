package frc.robot.util;

import java.net.URI;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class WebsocketButtonPad extends WebSocketClient {

    private WebsocketButton[][] buttons = new WebsocketButton[9][9];
    
	public WebsocketButtonPad(URI serverURI) {
        super( serverURI );

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.buttons[i][j] = new WebsocketButton();
            }
        }
	}

	public WebsocketButtonPad(URI serverUri, Map<String, String> httpHeaders) {
		super(serverUri, httpHeaders);
	}

	@Override
	public void onOpen( ServerHandshake handshakedata ) {
        System.out.println("Opened button server");   
    }

	@Override
	public void onMessage( String message ) {
        System.out.println( "received: " + message );
        String[] args = message.split(":");

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int val = Integer.parseInt(args[2]);

        if (val > 0) {
            buttons[x][y].set(true);
        } else {
            buttons[x][y].set(false);
        }

    }
    
    public WebsocketButton getButtonInstance (int x, int y) {
        return buttons[x][y];
    }

	@Override
	public void onClose( int code, String reason, boolean remote ) {
        System.out.println("Closing button server: " + reason);
    }

	@Override
	public void onError( Exception ex ) {
		ex.printStackTrace();
    }

}
