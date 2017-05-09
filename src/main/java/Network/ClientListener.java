package Network;

import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Platform;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */
public class ClientListener extends  Listener {

    private ClientConnection connection;
    private boolean fx;

    public ClientListener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener, ClientConnection connection, boolean fx){
        super( inputStream, cardframeworkListener);
        this.connection = connection;
        this.fx = fx;
    }

    public void run(){

        try {
            while (!quit) {
                final Message message = (Message) inputStream.readObject();
                if (message == null) connection.close();
                else if (fx) {Platform.runLater(() -> cardframeworkListener.processMessage(message));}
                     else cardframeworkListener.processMessage(message);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            if (fx) {Platform.runLater(() -> cardframeworkListener.closedConnection());}
            else cardframeworkListener.closedConnection();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}