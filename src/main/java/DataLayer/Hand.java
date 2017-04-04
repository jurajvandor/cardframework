package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import java.util.*;

public class Hand implements GroupOfCards {
	private HashSet<Card> cards;

	public Hand() {
		this.cards = new HashSet<>();
	}

	public Hand(HashSet<Card> cards) {
		this.cards = cards;
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

	public void merge(Hand cards) {
		this.cards.addAll(cards.cards);
	}

	public GroupOfCards copy(){
		return new Hand(new HashSet<>(cards));
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
}