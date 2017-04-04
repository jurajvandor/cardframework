package UI;

import DataLayer.Card;
import DataLayer.GroupOfCards;
import DataLayer.Hand;
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

    void show(){
        this.getChildren().clear();
        for (Card c: hand) {
            CardView card = new CardView(nameOfHand, playerId, c, showCards);
            card.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
                    handler.handleCardClick(c, playerId, nameOfHand);
                })
            );
            this.getChildren().add(card);
        }
    }
}