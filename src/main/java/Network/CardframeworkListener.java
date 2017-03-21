package Network;


/**
 * Created by Juraj Vandor on 14.03.2017.
 */
public interface CardframeworkListener {
    void processMessage(Message message);
    void closedConnection();
}
