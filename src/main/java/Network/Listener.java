package Network;

import java.io.BufferedReader;
import java.io.Closeable;

/**
 * Created by Juraj Vandor on 12.03.2017.
 */
public class Listener extends Thread implements Closeable{
    protected BufferedReader inputStream;
    protected boolean quit = false;
    protected CardframeworkListener cardframeworkListener;
    public void close(){
        quit = true;
    }

    Listener( BufferedReader inputStream, CardframeworkListener cardframeworkListener){
        this.inputStream = inputStream;
        this.cardframeworkListener = cardframeworkListener;
    }
}
