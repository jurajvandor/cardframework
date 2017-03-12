package Network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
class ClientListener extends  Listener {


    ClientListener(BlockingQueue<String> inputBuffer, BufferedReader inputStream){
        super(inputBuffer, inputStream);
    }

    public void run(){
        String message;
        try {
            while (!quit) {
                message = inputStream.readLine();
                inputBuffer.add(message);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}