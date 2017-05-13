package Rummy;

import UIUtils.*;
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
        DeckView draw = new DeckView("drawing", -1, dr, handler);
        DeckView discard = new DeckView("discard", -1, dis, handler);
        this.getChildren().addAll(draw, discard);
    }
}
