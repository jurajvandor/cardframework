package DataLayer;

/**
 * Created by Juraj on 15.02.2017.
 */
public interface GroupOfCards {

    public void addCard(Card card);
    public boolean removeCard(Card card);
    public boolean hasCard(Card card);
}
