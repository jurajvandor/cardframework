package Network;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
class ServerClientConnectionListener extends Thread implements Closeable {
    private BlockingQueue<String> inputBuffer;
    private DataInputStream inputStream;
    private boolean quit = false;

    public void close(){
        quit = true;
    }

    ServerClientConnectionListener(BlockingQueue<String> inputBuffer, DataInputStream inputStream){
        this.inputBuffer = inputBuffer;
        this.inputStream = inputStream;
    }

    public void run(){
        String message;
        try {
            while (!quit) {
                message = inputStream.readUTF();
                inputBuffer.add(message);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}