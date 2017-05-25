package cz.muni.fi.cardframework.UIUtils;

import cz.muni.fi.cardframework.DataLayer.Card;
import cz.muni.fi.cardframework.DataLayer.GroupOfCards;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Created by Juraj Vandor on 27.03.2017.
 */

/**
 * graphical view of hand
 */
public class HandView extends HBox{
    private GroupOfCards hand;
    private String nameOfHand;
    private int playerId;
    private boolean showCards;
    private PlayerActionHandler handler;

    /**
     * initiating values, cards in hand are overlapped to approximately half of their width
     * @param hand data for hand
     * @param nameOfHand name of hand
     * @param playerId owner's id of hand {-1 for Desk}
     * @param showCards true if cards are visible
     * @param handler handles player's clicks
     */
    public HandView(GroupOfCards hand, String nameOfHand, int playerId, boolean showCards, PlayerActionHandler handler) {
        this.hand = hand;
        this.nameOfHand = nameOfHand;
        this.playerId = playerId;
        this.showCards = showCards;
        this.handler = handler;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(-50);
    }

    public String getNameOfHand() {
        return nameOfHand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public GroupOfCards getHand() {
        return hand;
    }

    /**
     * shows/resets view of hand
     */
    public void show(){
        this.getChildren().clear();
        for (Card c: hand) {
            CardView card = new CardView(nameOfHand, playerId, c, showCards, handler);
            this.getChildren().add(card);
        }
    }
}
