package Network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
class ConnectionListener extends Thread implements Closeable {
    private BlockingQueue<String> inputBuffer;
    private BufferedReader inputStream;
    private boolean quit = false;

    public void close(){
        quit = true;
    }

    ConnectionListener(BlockingQueue<String> inputBuffer, BufferedReader inputStream){
        this.inputBuffer = inputBuffer;
        this.inputStream = inputStream;
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