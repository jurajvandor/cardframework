package DataLayer;

import java.io.Serializable;

/**
 * Created by Juraj Vandor on 15.02.2017.
 */

/**
 * type of the Deck (if cards are face up or face down)
 */
public enum DeckType implements Serializable {
    DISCARD,
    TAKE
}