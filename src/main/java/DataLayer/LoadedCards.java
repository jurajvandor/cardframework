package DataLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj Vandor on 13.02.2017.
 */
public class LoadedCards  implements Serializable {
    private ArrayList<LoadedCard> cards;
    private String name;

    /**
     * creates array of loaded cards (which represents whole deck)
     * @param cards loaded cards in deck
     * @param name name of cards
     */
    public LoadedCards(ArrayList<LoadedCard> cards, String name) {
        this.cards = cards;
        //this.typeValues = typeValues;
        this.name = name;
    }

    /**
     * @return name of cards
     */
    public String getName() {
        return name;
    }

    /**
     * creates deck with correct number of cards inside
     * @return created deck
     */
    public Deck createDeck(){
        ArrayList<Card> list = new ArrayList<>();
        for (LoadedCard c: cards) {
            for (int i = c.getCount(); i > 0; i--){
                list.add(new Card(c.getCard()));
            }
        }
        return new Deck(list, DeckType.TAKE);
    }
}
