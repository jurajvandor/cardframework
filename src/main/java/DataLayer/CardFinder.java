package DataLayer;

import java.util.Collection;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

/**
 * copies group of cards that it has been given and filters this copy which can be retrieved with getCards()
 */
public class CardFinder{

	GroupOfCards cards;

    /**
     * creates GroupOfCards copy
     * @param cards cards to filter
     */
	CardFinder(GroupOfCards cards){
		this.cards = cards.copy();
	}

    /**
     * filters group of cards that dont match values for given property type
     * (e.g. has king of hearts, queen of hearts and ace of hearts, property name is value, and values contains
     * {king, queen} so ace of hearts is removed)
     * @param propertyName name of property to filter by
     * @param values values to keep
     * @return filtered collection (could be also retrieved with getCards() )
     */
    public GroupOfCards filterCards(String propertyName, Collection<String> values) {
        for (Card c : cards){
            if (!values.contains(c.getProperties().get(propertyName))){
                cards.removeCard(c);
            }
        }
        return cards;
    }

    /**
     * filters group of cards that dont match values for given property type
     * (e.g. has king of hearts, queen of hearts, ace of spades and ace of hearts, property name is value, and value is
     * ace so king of hearts, and queen of hearts is removed), two aces remains
     * @param propertyName name of property to filter by
     * @param value value to keep
     * @return filtered collection (could be also retrieved with getCards() )
     */
    public GroupOfCards filterCards(String propertyName, String value) {
        for (Card c : cards){
            if (!c.getProperties().get(propertyName).equals(value)){
                cards.removeCard(c);
            }
        }
        return cards;
    }


    /**
     * @return copied and filtered collection of cards
     */
    public GroupOfCards getCards(){
		return cards;
	}

}