package DataLayer;

import java.io.Serializable;
import java.util.*;

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
/*for i from 0 to n−2 do
	j ← random integer such that i ≤ j < n
	exchange a[i] and a[j]
	https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle */
	public void shuffle() {
		for (int i = 0; i < cards.size()-2; i++){
			int j = Dice.roll(i,cards.size()-1);
			Card tmp = cards.get(i);
			cards.set(i, cards.get(j));
			cards.set(j, tmp);
		}
	}

	public DeckType getType() {
		return this.type;
	}

	public void setType(DeckType type) {
		this.type = type;
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

