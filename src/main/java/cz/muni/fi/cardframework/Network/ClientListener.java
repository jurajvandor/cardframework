package cz.muni.fi.cardframework.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javafx.application.Platform;

import javax.crypto.SealedObject;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */

/**
 * class used for listening to messages from server on client side
 */
public class ClientListener extends  Listener {

    private ClientConnection connection;
    private boolean fx;

    /**
     * listener to ClientConnection
     * @param inputStream input stream from server to client(this)
     * @param cardframeworkListener for handling closed connection
     * @param connection connection to server
     * @param fx if javaFX's Platform.runLater should be used
     * @param key DSF key for deciphering messages
     */
    public ClientListener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener, ClientConnection connection, boolean fx, Key key){
        super(inputStream, cardframeworkListener, key);
        this.connection = connection;
        this.fx = fx;
    }

    public void run() throws NetworkLayerException{

        try {
            while (!quit) {
                final SealedObject sealedMessage = (SealedObject) inputStream.readObject();
                Message message = null;
                try {
                    message = (Message) sealedMessage.getObject(symKey);
                }
                catch (NoSuchAlgorithmException | InvalidKeyException e){
                    throw new NetworkLayerException(e);
                }
                final Message m = message;
                if (message == null) connection.close();
                else if (fx) {Platform.runLater(() -> cardframeworkListener.processMessage(m));}
                     else cardframeworkListener.processMessage(message);
            }
        }
        catch (IOException e){
            if (fx) {Platform.runLater(() -> cardframeworkListener.closedConnection());}
            else cardframeworkListener.closedConnection();
            throw new NetworkLayerException(e);
        }
        catch (ClassNotFoundException e){
            throw new NetworkLayerException(e);
        }
    }
}