package cz.muni.fi.cardframework.UIUtils;

import cz.muni.fi.cardframework.DataLayer.Deck;
import cz.muni.fi.cardframework.DataLayer.DeckType;

/**
 * Created by Juraj on 13.05.2017.
 */

/**
 * graphical view of Deck
 */
public class DeckView extends CardView{

    /**
     * shows just top card of deck, visibility is according to deck type
     * @param nameOfDeck deck name
     * @param playerId id of owner (-1 for Desk)
     * @param deck data to be shown
     * @param handler handler for player click
     */
    public DeckView(String nameOfDeck, int playerId, Deck deck, PlayerActionHandler handler){
        super(nameOfDeck, playerId, deck.getTopCard(), deck.getType() == DeckType.DISCARD, handler);
    }
}
