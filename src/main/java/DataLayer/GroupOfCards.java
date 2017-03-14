package DataLayer;

import java.io.Serializable;

/**
 * Created by Juraj Vandor on 15.02.2017.
 */
public interface GroupOfCards extends Iterable<Card>, Serializable {


    public GroupOfCards copy();
    public void addCard(Card card);
    public boolean removeCard(Card card);
    public boolean hasCard(Card card);
}
