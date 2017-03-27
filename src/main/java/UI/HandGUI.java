package UI;

import DataLayer.Card;
import DataLayer.Hand;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Juraj on 27.03.2017.
 */
public class HandGUI extends HBox{
    private Hand hand;
    private String nameOfHand;
    private int playerId;
    private boolean showCards;

    public HandGUI(Hand hand, String nameOfHand, int playerId, boolean showCards) {
        this.hand = hand;
        this.nameOfHand = nameOfHand;
        this.playerId = playerId;
        this.showCards = showCards;
    }

    public String getNameOfHand() {
        return nameOfHand;
    }

    public int getPlayerId() {
        return playerId;
    }

    void show(){
        this.getChildren().clear();
        for (Card c: hand) {
            CardGUI card = new CardGUI(nameOfHand, playerId, c);
            card.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
                    System.out.println("Tile pressed ");
                    event.consume();
                })
            );

            this.getChildren().add(card);
        }
    }
}
