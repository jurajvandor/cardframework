package DataLayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CardOwner  implements Serializable {

    private Map<String, GroupOfCards> cards;

    public CardOwner(){
        cards = new HashMap<String,GroupOfCards>();
    }
    public void addCards(String name, GroupOfCards cards){
        this.cards.put(name, cards);
    }

    public GroupOfCards getCards(String name){
        return cards.get(name);
    }


}