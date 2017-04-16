package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

import java.util.*;

public class Hand implements GroupOfCards {
	private TreeSet<Card> cards;

	/**
	 * creates empty hand
	 */
	public Hand() {
		this.cards = new TreeSet<>();
	}

	/**
	 * creates hand with given cards
	 * @param cards cards to be added
	 */
	public Hand(SortedSet<Card> cards) {
		this.cards = new TreeSet<>(cards);
	}

	/**
	 * @return unmodifiable set of cards in hand
	 */
	public SortedSet<Card> getCards() {
		return Collections.unmodifiableSortedSet(cards);
	}

	/**
	 * adds card to hand
	 * @param card card to be added
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * removes card from hand
	 * @param card card to be removed
	 * @return true if successful
	 */
	public boolean removeCard(Card card) {
		return cards.remove(card);
	}

	/**
	 * removes cards from hand
	 * @param cards collection of cards to be removed
	 * @return true if successful
	 */
	public boolean removeCards(Collection<Card> cards) {
		return this.cards.removeAll(cards);
	}

	/**
	 * removes cards from hand
	 * @param cards hand of cards to be removed
	 * @return true if successful
	 */
	public boolean removeCards(Hand cards) {
		return this.cards.removeAll(cards.cards);
	}

	/**
	 * checks if card is in hand
	 * @param card card to be found
	 * @return true if found
	 */
	public boolean hasCard(Card card) {
		return cards.contains(card);
	}

	/**
	 * merges another hanad into this one
	 * @param cards another hand
	 */
	public void merge(Hand cards) {
		this.cards.addAll(cards.cards);
	}

	/**
	 * copies hand into group of cards (could be casted to hand)
	 * @return copied group of cards
	 */
	public GroupOfCards copy(){
		return new Hand(new TreeSet<>(cards));
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
}