import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Deck extends GroupOfCards {

	private boolean type;

	public Deck(List<Card> cards ){
		super(cards);
	}



	public Card drawTopCard() {
		// TODO - implement Deck.drawTopCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param card
	 */
	public void returnCardToTop(Card card) {
		// TODO - implement Deck.returnCardToTop
		throw new UnsupportedOperationException();
	}

	public void shuffle() {
		// TODO - implement Deck.shuffle
		throw new UnsupportedOperationException();
	}

	public boolean getType() {
		return this.type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(boolean type) {
		this.type = type;
	}

}