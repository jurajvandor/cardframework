package DataLayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

/**
 * represents card owner (in basic version of this framework can be Desk or Player
 */
public class CardOwner  implements Serializable {

    private HashMap<String, GroupOfCards> cards;

    /**
     * creates map of groups of cards with string (name) as a key
     */
    public CardOwner(){
        cards = new HashMap<>();
    }

    /**
     * copies map of groups of cards
     * @param cards map that stores groups of cards with name as a key
     */
    public CardOwner(Map<String, GroupOfCards> cards){
        cards = new HashMap<>(cards);
    }

    /**
     * adds group of cards with given name to owner
     * @param name key to be stored with (must be unique or value is rewritten)
     * @param cards group of cards to be stored
     */
    public void addCards(String name, GroupOfCards cards){
        this.cards.put(name, cards);
    }

    /**
     * @param name key under which group of cards is stored
     * @return group of cards under given name (could be null if key does not exit)
     */
    public GroupOfCards getCards(String name){
        return cards.get(name);
    }

    public Deck getDeck(String name){
        GroupOfCards c = cards.get(name);
        if (c instanceof Deck)
            return (Deck)c;
        return null;
    }

    public Hand getHand(String name){
        GroupOfCards c = cards.get(name);
        if (c instanceof Hand)
            return (Hand)c;
        return null;
    }
}