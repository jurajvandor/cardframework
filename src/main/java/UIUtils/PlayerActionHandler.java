package UIUtils;

import DataLayer.Card;

/**
 * Created by Juraj Vandor on 28.03.2017.
 */
public interface PlayerActionHandler {
    void handleCardClick(Card card, int playerId, String nameOfHand);
}
