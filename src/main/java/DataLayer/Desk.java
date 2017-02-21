package DataLayer;

import java.io.Serializable;

public class Desk extends CardOwner implements Serializable {
    private Deck deck;
    Deck getDeck(){
        return deck;
    }
}