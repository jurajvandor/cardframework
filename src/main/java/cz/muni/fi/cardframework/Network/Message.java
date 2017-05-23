package cz.muni.fi.cardframework.Network;

import java.io.Serializable;

/**
 * Created by Juraj Vandor on 21.03.2017.
 */

/**
 * class used for sending all messages (text only or text+object)
 */
public class Message implements Serializable{
    private String message;
    private Serializable object;

    /**
     * creates message with text message and serializable object
     * @param message text message
     * @param object object to be send
     */
    public Message(String message, Serializable object) {
        this.message = message;
        this.object = object;
    }


    /**
     * creates message with text message only with object = null
     * @param message text message
     */
    public Message(String message) {
        this.message = message;
        this.object = null;
    }

    /**
     * @return text message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message sets text message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return object (could be null)
     */
    public Object getObject() {
        return object;
    }
}
