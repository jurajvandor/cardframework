package DataLayer;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
public class CardFinder{

	GroupOfCards cards;

	CardFinder(GroupOfCards cards){
		this.cards = cards.copy();
	}
    public GroupOfCards filterCards(String propertyName, Collection<String> values) {
        for (Card c : cards){
            if (!values.contains(c.getProperties().get(propertyName))){
                cards.removeCard(c);
            }
        }
        return cards;
    }

    public GroupOfCards filterCards(String propertyName, String value) {
        for (Card c : cards){
            if (!c.getProperties().get(propertyName).equals(value)){
                cards.removeCard(c);
            }
        }
        return cards;
    }


    public GroupOfCards getCards(){
		return cards;
	}

}