package DataLayer;

import java.io.Serializable;
import java.util.*;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

/**
 * represents pile of cards where top card can be accessed
 */
public class Deck implements GroupOfCards, Serializable {

	private DeckType type;

	private ArrayList<Card> cards;

	/**
	 * creates deck
	 * @param cards initial array of cards
	 * @param type type of deck (face up or face down)
	 */
	public Deck(List<Card> cards, DeckType type){
		this.cards = new ArrayList<>(cards);
		this.type = type;
	}

	/**
	 * creates empty deck
	 * @param type type of deck (face up or face down)
	 */
	public Deck(DeckType type){
		this.cards = new ArrayList<>();
		this.type = type;
	}

	/**
	 * removes top card from deck and returns it
	 * @return top card from deck
	 */
	public Card drawTopCard() {
		if (cards.size() == 0)
			return null;
		return cards.remove(cards.size()-1);
	}

	/**
	 * @return top card from deck
	 */
	public Card getTopCard() {
		if (cards.size() == 0)
			return null;
		return cards.get(cards.size()-1);
	}

	/**
	 * shuffles deck to random order
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * @return type of deck (face up or face down)
	 */
	public DeckType getType() {
		return this.type;
	}

	/**
	 * sets deck type to given type and if it is differet reverses order of cards
	 * @param type type to be set
	 */
	public void setType(DeckType type) {
		if (type != this.type) {
			this.type = type;
			Collections.reverse(cards);
		}
	}

	/**
	 * @return unmodifiable list of cards
	 */
	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	/**
	 * adds card to the top of deck
	 * @param card to be added
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * removes card from deck
	 * @param card card to be removed
	 * @return if it was successful
	 */
	public boolean removeCard(Card card) {
		return cards.remove(card);
	}

	/**
	 * checks if collection contains card
	 * @param card card to be found
	 * @return if collection contains card
	 */
	public boolean hasCard(Card card) {
		return cards.contains(card);
	}

	/**
	 * creates copy of Deck and returns it
	 * @return copy of Deck
	 */
	public GroupOfCards copy(){
		return new Deck(new ArrayList<>(cards), type);
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	/**
	 * merges two decks (another deck on top this deck)
	 * @param cards deck to be added
	 */
	public void merge(Deck cards){
		this.cards.addAll(cards.cards);
	}
}

