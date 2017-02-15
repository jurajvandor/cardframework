package DataLayer;

import java.util.Collection;
import java.util.List;

public class ListOfCards implements GroupOfCards {

	private List<Card> cards;

	/**
	 * 
	 * @param cards
	 */
	public ListOfCards(List<Card> cards) {
		this.cards = cards;
	}

	public Collection<Card> getCards() {
		return cards;
	}

	/**
	 * 
	 * @param card
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * 
	 * @param card
	 */
	public boolean removeCard(Card card) {
		return cards.remove(card);

	}

	/**
	 * 
	 * @param card
	 */
	public boolean hasCard(Card card) {
		return cards.contains(card);
	}

}