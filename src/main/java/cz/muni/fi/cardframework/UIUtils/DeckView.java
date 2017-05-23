package cz.muni.fi.cardframework.UIUtils;

import cz.muni.fi.cardframework.DataLayer.Deck;
import cz.muni.fi.cardframework.DataLayer.DeckType;

/**
 * Created by Juraj on 13.05.2017.
 */
public class DeckView extends CardView{

    public DeckView(String nameOfHand, int playerId, Deck deck, PlayerActionHandler handler){
        super(nameOfHand, playerId, deck.getTopCard(), deck.getType() == DeckType.DISCARD, handler);
    }
}
