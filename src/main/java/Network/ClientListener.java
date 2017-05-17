package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javafx.application.Platform;

import javax.crypto.SealedObject;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */
public class ClientListener extends  Listener {

    private ClientConnection connection;
    private boolean fx;

    public ClientListener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener, ClientConnection connection, boolean fx, Key key){
        super(inputStream, cardframeworkListener, key);
        this.connection = connection;
        this.fx = fx;
    }

    public void run(){

        try {
            while (!quit) {
                final SealedObject sealedMessage = (SealedObject) inputStream.readObject();
                Message message = null;
                try {
                    message = (Message) sealedMessage.getObject(symKey);
                }
                catch (NoSuchAlgorithmException | InvalidKeyException e){
                    e.printStackTrace();
                }
                final Message m = message;
                if (message == null) connection.close();
                else if (fx) {Platform.runLater(() -> cardframeworkListener.processMessage(m));}
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