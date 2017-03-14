package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import java.io.Serializable;

public class Desk extends CardOwner implements Serializable {
    private Deck deck;
    Deck getDeck(){
        return deck;
    }
}