package UI;

import DataLayer.Card;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.event.HyperlinkEvent;

/**
 * Created by Juraj Vandor on 27.03.2017.
 */
public class CardView extends ImageView {
    private String nameOfHand;
    private int playerId;
    private Card card;

    public CardView(String nameOfHand, int playerId, Card card, boolean showCard) {
        if (showCard)
            this.setImage(new Image(card.getProperty("picture")));
        else
            this.setImage(new Image("/card_back.png"));
        this.setFitHeight(120);
        this.setFitWidth(80);
        this.nameOfHand = nameOfHand;
        this.playerId = playerId;
        this.card = card;
    }

    public String getNameOfHand() {
        return nameOfHand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Card getCard() {
        return card;
    }
}
