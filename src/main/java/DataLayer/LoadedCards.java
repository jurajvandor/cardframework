package DataLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj Vandor on 13.02.2017.
 */
public class LoadedCards  implements Serializable {
    private List<LoadedCard> cards;
    private String name;

    public String getName() {
        return name;
    }

    public LoadedCards(List<LoadedCard> cards, String name) {
        this.cards = cards;
        //this.typeValues = typeValues;
        this.name = name;
    }

    public Deck createDeck(){
        List<Card> list = new ArrayList<>();
        for (LoadedCard c: cards) {
            for (int i = c.getCount(); i > 0; i--){
                list.add(new Card(c.getCard()));
            }
        }
        return new Deck(list);
    }
}
