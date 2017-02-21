package DataLayer;

import java.io.Serializable;

/**
 * Created by Juraj on 13.02.2017.
 */
public class LoadedCard  implements Serializable {
    private int count;
    private Card card;

    public LoadedCard(Card card, int count){
        this.card = card;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Card getCard() {
        return card;
    }
}
