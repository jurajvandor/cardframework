package UIUtils;

import DataLayer.Card;
import DataLayer.Deck;
import DataLayer.DeckType;
import javafx.scene.input.MouseEvent;

/**
 * Created by Juraj on 13.05.2017.
 */
public class DeckView extends CardView{

    public DeckView(String nameOfHand, int playerId, Deck deck, PlayerActionHandler handler){
        super(nameOfHand, playerId, deck.getTopCard(), deck.getType() == DeckType.DISCARD, handler);
    }
}
