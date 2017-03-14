package DataLayer;

import java.io.Serializable;
import java.util.*;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
public class Deck implements GroupOfCards, Serializable {

	private DeckType type;

	private List<Card> cards;

	public Deck(List<Card> cards ){
		this.cards = cards;
	}
	public Card drawTopCard() {
		return cards.remove(cards.size());
	}

	public void returnCardToTop(Card card) {
		cards.add(card);
	}


	public void shuffle() {
		Collections.shuffle(cards);
	}

	public DeckType getType() {
		return this.type;
	}

	public void setType(DeckType type) {
		this.type = type;
		Collections.reverse(cards);
	}

	public Collection<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public boolean removeCard(Card card) {
		return cards.remove(card);
	}

	public boolean hasCard(Card card) {
		return cards.contains(card);
	}


	public GroupOfCards copy(){
		return new Deck(new ArrayList<>(cards));
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
}

