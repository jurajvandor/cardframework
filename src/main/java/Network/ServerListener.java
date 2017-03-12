package Network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Juraj on 12.03.2017.
 */
public class ServerListener extends Listener {
    private final int id;


    ServerListener(BlockingQueue<String> inputBuffer, BufferedReader inputStream, int id){
        super(inputBuffer,inputStream);
        this.id = id;
    }

    public void run(){
        String message;
        try {
            while (!quit) {
                message = inputStream.readLine();
                inputBuffer.add(id + " " + message);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}