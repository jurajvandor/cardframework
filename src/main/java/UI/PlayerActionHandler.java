package UI;

import DataLayer.Card;

/**
 * Created by Juraj on 28.03.2017.
 */
public interface PlayerActionHandler {
    void handleCardClick(Card card, int playerId, String nameOfHand);
}
