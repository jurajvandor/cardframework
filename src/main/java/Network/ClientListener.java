package Network;

import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Platform;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */
public class ClientListener extends  Listener {

    private ClientConnection connection;

    public ClientListener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener, ClientConnection connection){
        super( inputStream, cardframeworkListener);
        this.connection = connection;
    }

    public void run(){

        try {
            while (!quit) {
                final Message message = (Message) inputStream.readObject();
                if (message == null) connection.close();
                else Platform.runLater(() -> cardframeworkListener.processMessage(message));
            }
        }
        catch (IOException e){
            e.printStackTrace();
            Platform.runLater(() -> cardframeworkListener.closedConnection());
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}