package Network;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.security.Key;

/**
 * Created by Juraj Vandor on 12.03.2017.
 */
public class Listener extends Thread implements Closeable{
    protected ObjectInputStream inputStream;
    protected boolean quit = false;
    protected CardframeworkListener cardframeworkListener;
    protected Key symKey;
    public void close(){
        quit = true;
    }

    Listener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener, Key key){
        this.inputStream = inputStream;
        this.cardframeworkListener = cardframeworkListener;
        this.symKey = key;
    }
}
