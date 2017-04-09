package UI;

import DataLayer.Card;
import DataLayer.Deck;
import DataLayer.Desk;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Created by Juraj Vandor on 03.04.2017.
 */
public class DeskView extends HBox{
    private Desk desk;
    private PlayerActionHandler handler;
    public DeskView(Desk desk, PlayerActionHandler handler){
        this.desk = desk;
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.handler = handler;
    }

    public void show(){
        this.getChildren().clear();
        Deck dr = desk.getDeck("drawing");
        Deck dis = desk.getDeck("discard");
        CardView draw = new CardView("drawing", -1, dr.getTopCard(), false );
        CardView discard = new CardView("discard", -1, dis.getTopCard(), true);
        draw.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
            handler.handleCardClick(null, -1, "drawing");
        }));
        discard.addEventHandler(MouseEvent.MOUSE_CLICKED,(event -> {
            handler.handleCardClick(null, -1, "discard");
        }));
        this.getChildren().addAll(draw, discard);
    }
}
