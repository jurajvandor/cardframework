package DataLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Deck implements GroupOfCards {

	private DeckType type;

	private List<Card> cards;

	public Deck(List<Card> cards ){
		this.cards = cards;
	}
	public Card drawTopCard() {
		return cards.get(cards.size());
	}

	public void returnCardToTop(Card card) {

	}

	public void shuffle() {

	}

	public DeckType getType() {
		return this.type;
	}

	public void setType(DeckType type) {
		this.type = type;
	}

	public Collection<Card> getCards() {
		return cards;
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

