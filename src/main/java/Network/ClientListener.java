package Network;

import UI.FXListener;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;

/**
 * Created by Juraj on 28.02.2017.
 */
public class ClientListener extends  Listener {


    public ClientListener(BufferedReader inputStream, FXListener fxListener){
        super( inputStream, fxListener);
    }

    public void run(){

        try {
            while (!quit) {
                final String message = inputStream.readLine();
                Platform.runLater(() -> fxListener.processMessage(message));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}