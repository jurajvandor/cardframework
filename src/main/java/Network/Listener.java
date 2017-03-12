package Network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Juraj on 12.03.2017.
 */
public class Listener extends Thread implements Closeable{
    protected BlockingQueue<String> inputBuffer;
    protected BufferedReader inputStream;
    protected boolean quit = false;

    public void close(){
        quit = true;
    }

    Listener(BlockingQueue<String> inputBuffer, BufferedReader inputStream){
        this.inputBuffer = inputBuffer;
        this.inputStream = inputStream;
    }
}
