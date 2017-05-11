package UIUtils;

import DataLayer.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Juraj Vandor on 27.03.2017.
 */
public class CardView extends ImageView {
    private String nameOfCards;
    private int playerId;
    private Card card;

    public CardView(String nameOfHand, int playerId, Card card, boolean showCard) {
        if (card == null){
            this.setImage(new Image("/null_card.png"));
        }
        else {
            if (showCard)
                this.setImage(new Image(card.getProperty("picture")));
            else
                this.setImage(new Image("/card_back.png"));
        }
        this.setFitHeight(120);
        this.setFitWidth(80);
        this.nameOfCards = nameOfHand;
        this.playerId = playerId;
        this.card = card;
    }

    public void setHalf(){
        this.setFitWidth(40);
    }

    public void setFull(){
        this.setFitWidth(40);
    }

    public String getNameOfCards() {
        return nameOfCards;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Card getCard() {
        return card;
    }
}
