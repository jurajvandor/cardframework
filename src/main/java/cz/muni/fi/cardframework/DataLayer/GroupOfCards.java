package cz.muni.fi.cardframework.DataLayer;

import java.io.Serializable;

/**
 * Created by Juraj Vandor on 15.02.2017.
 */
public interface GroupOfCards extends Iterable<Card>, Serializable {
    /**
     * creates copy of group of cards
     * @return copied group
     */
    public GroupOfCards copy();

    /**
     * adds card to group
     * @param card card to be added
     */
    public void addCard(Card card);

    /**
     * removes card from group
     * @param card card to be removed
     * @return true if successful
     */
    public boolean removeCard(Card card);

    /**
     * checks if card is in group
     * @param card card to be found
     * @return true if it is
     */
    public boolean hasCard(Card card);
}
