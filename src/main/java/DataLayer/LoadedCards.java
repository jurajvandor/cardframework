package DataLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj on 13.02.2017.
 */
public class LoadedCards {
    private List<LoadedCard> cards;
    //private Set<DataLayer.CardPropertyTypeValues> typeValues;
    private String name;

    public LoadedCards(List<LoadedCard> cards,  String name) {
        this.cards = cards;
        //this.typeValues = typeValues;
        this.name = name;
    }

    public Deck createDeck(){
        List<Card> list = new ArrayList<Card>();
        for (LoadedCard c: cards) {
            for (int i = c.getCount(); i > 0; i--){
                list.add(c.getCard().copyCard());
            }
        }
        return new Deck(list);
    }
}
