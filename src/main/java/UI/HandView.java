package UI;

import DataLayer.Card;
import DataLayer.Hand;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Created by Juraj Vandor on 27.03.2017.
 */
public class HandView extends HBox{
    private Hand hand;
    private String nameOfHand;
    private int playerId;
    private boolean showCards;
    private PlayerActionHandler handler;

    public HandView(Hand hand, String nameOfHand, int playerId, boolean showCards, PlayerActionHandler handler) {
        this.hand = hand;
        this.nameOfHand = nameOfHand;
        this.playerId = playerId;
        this.showCards = showCards;
        this.handler = handler;
    }

    public String getNameOfHand() {
        return nameOfHand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Hand getHand() {
        return hand;
    }

    void show(){
        this.getChildren().clear();
        for (Card c: hand) {
            CardView card = new CardView(nameOfHand, playerId, c);
            card.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
                    handler.handleCardClick(c, playerId, nameOfHand);
                })
            );
            this.getChildren().add(card);
        }
    }
}
