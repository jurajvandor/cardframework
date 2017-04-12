package DataLayer;

import java.io.Serializable;

/**
 * Created by Juraj Vandor on 13.02.2017.
 */
public class LoadedCard  implements Serializable {
    private int count;
    private Card card;

    /**
     * creates loaded card which
     * @param card card with its properties
     * @param count count of cards in deck
     */
    public LoadedCard(Card card, int count){
        this.card = card;
        this.count = count;
    }

    /**
     * @return count of cards
     */
    public int getCount() {
        return count;
    }

    /**
     * @return card with its properties
     */
    public Card getCard() {
        return card;
    }
}
