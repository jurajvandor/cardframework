package Network;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Juraj Vandor on 12.03.2017.
 */
public class ServerListener extends Listener {
    private final int id;

    private ServerConnectionToClient connection;

    ServerListener(ObjectInputStream inputStream, int id, CardframeworkListener cardframeworkListener, ServerConnectionToClient connection){
        super(inputStream, cardframeworkListener);
        this.id = id;
        this.connection = connection;
    }

    public void run(){
        try {
            while (!quit) {
                final Message message = (Message) inputStream.readObject();
                if (message == null) connection.close();
                else {
                    message.setMessage(id + " " + message.getMessage());
                    cardframeworkListener.processMessage(message);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e ){
            e.printStackTrace();
        }
    }
}