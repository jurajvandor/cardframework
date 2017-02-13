/**
 * Created by Juraj on 13.02.2017.
 */
public class LoadedCard {
    private int number;
    private Card card;

    public LoadedCard(Card card, int num){
        this.card = card;
        this.number = num;
    }

    public int getNumber() {
        return number;
    }

    public Card getCard() {
        return card;
    }
}
