package Network;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;

/**
 * Created by Juraj on 28.02.2017.
 */
public class ClientListener extends  Listener {

    private ClientConnection connection;

    public ClientListener(BufferedReader inputStream, CardframeworkListener cardframeworkListener, ClientConnection connection){
        super( inputStream, cardframeworkListener);
        this.connection = connection;
    }

    public void run(){

        try {
            while (!quit) {
                final String message = inputStream.readLine();
                if (message == null) connection.close();
                else Platform.runLater(() -> cardframeworkListener.processMessage(message));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}