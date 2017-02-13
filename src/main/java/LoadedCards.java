import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj on 13.02.2017.
 */
public class LoadedCards {
    private List<LoadedCard> cards;
    private List<CardPropertyTypeValues> typeValues;

    public LoadedCards(List<LoadedCard> cards, List<CardPropertyTypeValues> typeValues) {
        this.cards = cards;
        this.typeValues = typeValues;
    }

    public Deck createDeck(){
        List<Card> list = new ArrayList<Card>();
        for (LoadedCard c: cards) {
            for (int i = c.getNumber(); i > 0; i--){
                list.add(c.getCard().copyCard());
            }
        }
        return new Deck(list);
    }
}
