package Network;

import UI.FXListener;

import java.io.BufferedReader;
import java.io.Closeable;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Juraj on 12.03.2017.
 */
public class Listener extends Thread implements Closeable{
    protected BufferedReader inputStream;
    protected boolean quit = false;
    protected FXListener fxListener;
    public void close(){
        quit = true;
    }

    Listener( BufferedReader inputStream, FXListener fxListener){
        this.inputStream = inputStream;
        this.fxListener = fxListener;
    }
}
