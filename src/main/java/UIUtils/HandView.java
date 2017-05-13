package UIUtils;

import DataLayer.Card;
import DataLayer.GroupOfCards;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Created by Juraj Vandor on 27.03.2017.
 */
public class HandView extends HBox{
    private GroupOfCards hand;
    private String nameOfHand;
    private int playerId;
    private boolean showCards;
    private PlayerActionHandler handler;

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

    public void show(){
        this.getChildren().clear();
        for (Card c: hand) {
            CardView card = new CardView(nameOfHand, playerId, c, showCards, handler);
            this.getChildren().add(card);
        }
    }
}
