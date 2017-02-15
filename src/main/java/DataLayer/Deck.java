package DataLayer;

import java.util.List;

public class Deck extends ListOfCards {

	private boolean type;

	public Deck(List<Card> cards ){
		super(cards);
	}



	public Card drawTopCard() {
		cards
	}

	/**
	 * 
	 * @param card
	 */
	public void returnCardToTop(Card card) {
		// TODO - implement DataLayer.Deck.returnCardToTop
		throw new UnsupportedOperationException();
	}

	public void shuffle() {
		// TODO - implement DataLayer.Deck.shuffle
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