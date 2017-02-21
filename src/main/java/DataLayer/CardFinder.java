package DataLayer;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CardFinder  implements Serializable {

	GroupOfCards cards;

	CardFinder(GroupOfCards cards){
		this.cards = cards.copy();
	}
	public GroupOfCards FilterCards(String propertyName, Collection<String> values) {

		return cards;
	}

	public GroupOfCards getCards(){
		return cards;
	}

}