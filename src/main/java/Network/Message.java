package Network;

import java.io.Serializable;

/**
 * Created by Juraj Vandor on 21.03.2017.
 */
public class Message implements Serializable{
    private String message;
    private Serializable object;

    public Message(String message, Serializable object) {
        this.message = message;
        this.object = object;
    }
    public Message(String message) {
        this.message = message;
        this.object = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }
}
