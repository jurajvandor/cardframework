package Network;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Juraj on 12.03.2017.
 */
public class ServerListener extends Listener {
    private final int id;

    private ServerConnectionToClient connection;

    ServerListener( BufferedReader inputStream, int id, CardframeworkListener cardframeworkListener, ServerConnectionToClient connection){
        super(inputStream, cardframeworkListener);
        this.id = id;
        this.connection = connection;
    }

    public void run(){
        String message;
        try {
            while (!quit) {
                message = inputStream.readLine();
                if (message == null) connection.close();
                else cardframeworkListener.processMessage(id + " " + message);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}