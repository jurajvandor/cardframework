package UI;

import DataLayer.Card;
import DataLayer.Hand;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Juraj on 11.04.2017.
 */
public class MeldView extends TextFlow{
    private String name;
    private Hand meld;

    public MeldView(String name, Hand meld){
        this.name = name;
        this.meld = meld;
        for (Card c : meld){
            this.getChildren().add(StaticUtils.getShortToString(c));
        }
    }

    public void add(Card card){
        meld.addCard(card);
        this.getChildren().clear();
        for (Card c : meld){
            this.getChildren().add(StaticUtils.getShortToString(c));
        }
    }

    public String getName() {
        return name;
    }

    public Hand getMeld() {
        return meld;
    }

    public void reset(){
        meld = new Hand();
        this.getChildren().clear();
    }
}
