package cz.muni.fi.cardframework.UIUtils;

import cz.muni.fi.cardframework.DataLayer.Card;

/**
 * Created by Juraj Vandor on 28.03.2017.
 */

/**
 * interface for handling player's actions (clicking on cards)
 */
public interface PlayerActionHandler {
    /**
     * handles card clicks, or ignores invalid ones according to game state, owner and group of cards
     * @param card card that player had clicked on
     * @param ownerId id of card owner
     * @param nameOfGroup name of card's group of cards
     */
    void handleCardClick(Card card, int ownerId, String nameOfGroup);
}
