package cz.muni.fi.cardframework.Network;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.security.Key;

/**
 * Created by Juraj Vandor on 12.03.2017.
 */

/**
 * basic Listener (common things used for server and client)
 */
public class Listener extends Thread implements Closeable{
    protected ObjectInputStream inputStream;
    protected boolean quit = false;
    protected CardframeworkListener cardframeworkListener;
    protected Key symKey;

    /**
     * sets quit to true
     */
    public void close(){
        quit = true;
    }

    /**
     * initiates common values
     * @param inputStream input stream from server to client(this)
     * @param cardframeworkListener object for message handling
     * @param key DES key for deciphering
     */
    Listener(ObjectInputStream inputStream, CardframeworkListener cardframeworkListener, Key key){
        this.inputStream = inputStream;
        this.cardframeworkListener = cardframeworkListener;
        this.symKey = key;
    }
}
