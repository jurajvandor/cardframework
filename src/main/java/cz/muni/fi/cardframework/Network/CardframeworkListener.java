package cz.muni.fi.cardframework.Network;


/**
 * Created by Juraj Vandor on 14.03.2017.
 */

/**
 * interface that must be implemented in order to handle incoming messages
 */
public interface CardframeworkListener {
    /**
     * processes received messages
     * @param message received message
     */
    void processMessage(Message message);

    /**
     * should contain handling of closed connection
     */
    void closedConnection();
}
