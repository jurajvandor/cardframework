package cz.muni.fi.cardframework.Rummy;

import cz.muni.fi.cardframework.DataLayer.Deck;
import cz.muni.fi.cardframework.DataLayer.Desk;
import cz.muni.fi.cardframework.UIUtils.DeckView;
import cz.muni.fi.cardframework.UIUtils.PlayerActionHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Created by Juraj Vandor on 03.04.2017.
 */

/**
 * represents view of cards on Desk
 */
public class DeskView extends HBox{
    private Desk desk;
    private PlayerActionHandler handler;

    /**
     * initiates values
     * @param desk desk to be shown
     * @param handler object that handles player's clicks
     */
    public DeskView(Desk desk, PlayerActionHandler handler){
        this.desk = desk;
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.handler = handler;
    }

    /**
     * shows desk and resets view to current state of Decks
     */
    public void show(){
        this.getChildren().clear();
        Deck dr = desk.getDeck("drawing");
        Deck dis = desk.getDeck("discard");
        DeckView draw = new DeckView("drawing", -1, dr, handler);
        DeckView discard = new DeckView("discard", -1, dis, handler);
        this.getChildren().addAll(draw, discard);
    }
}
