package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import java.util.*;

public class Hand implements GroupOfCards {

	private boolean visibility;
	private Set<Card> cards;

	public Hand(Set<Card> cards, boolean visibility) {
		this.cards = cards;
		this.visibility = visibility;
	}

	public boolean getVisibility() {
		return this.visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
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
		return new Hand(new TreeSet<>(cards), visibility);
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
}