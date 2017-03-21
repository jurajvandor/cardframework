package Network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.ObjectInputStream;

/**
 * Created by Juraj Vandor on 12.03.2017.
 */
public class Listener extends Thread implements Closeable{
    protected ObjectInputStream inputStream;
    protected boolean quit = false;
    protected CardframeworkListener cardframeworkListener;
    public void close(){
        quit = true;
    }

    Listener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener){
        this.inputStream = inputStream;
        this.cardframeworkListener = cardframeworkListener;
    }
}
