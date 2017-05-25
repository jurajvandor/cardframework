package cz.muni.fi.cardframework.UIUtils;

import cz.muni.fi.cardframework.DataLayer.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Created by Juraj Vandor on 27.03.2017.
 */

/**
 * graphical view of single card
 */
public class CardView extends ImageView {
    private String nameOfCards;
    private int playerId;
    private Card card;

    /**
     * initiates view and adds event handler for mouse click 
     * @param nameOfGroup name of GroupOfCards where card
     * @param playerId id of owner (-1 if Desk is owner)
     * @param card card's data
     * @param showCard true if it is visible
     * @param handler handler for player's clicks
     */
    public CardView(String nameOfGroup, int playerId, Card card, boolean showCard, PlayerActionHandler handler) {
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
        this.nameOfCards = nameOfGroup;
        this.playerId = playerId;
        this.card = card;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
            handler.handleCardClick(card, playerId, nameOfGroup);
        })
        );
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
