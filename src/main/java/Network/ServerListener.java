package Network;

import javax.crypto.SealedObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by Juraj Vandor on 12.03.2017.
 */
public class ServerListener extends Listener {
    private final int id;
    private ServerConnectionToClient connection;

    ServerListener(ObjectInputStream inputStream, int id, CardframeworkListener cardframeworkListener, ServerConnectionToClient connection, Key key){
        super(inputStream, cardframeworkListener, key);
        this.id = id;
        this.connection = connection;
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
                if (message == null) connection.close();
                else {
                    message.setMessage(id + " " + message.getMessage());
                    cardframeworkListener.processMessage(message);
                }
            }
        }
        catch (IOException e){
            throw new NetworkLayerException(e);
        }
        catch (ClassNotFoundException e ){
            throw new NetworkLayerException(e);
        }
    }
}