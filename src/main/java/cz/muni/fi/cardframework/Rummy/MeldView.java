package cz.muni.fi.cardframework.Rummy;

import cz.muni.fi.cardframework.DataLayer.Card;
import cz.muni.fi.cardframework.DataLayer.Hand;
import javafx.scene.text.TextFlow;

/**
 * Created by Juraj on 11.04.2017.
 */

/**
 * graphical view of meld (shows hand as text only inside TextFlow)
 */
public class MeldView extends TextFlow{
    private String name;
    private Hand meld;

    /**
     * initiates values
     * @param name name of meld
     * @param meld where are cards stored
     */
    public MeldView(String name, Hand meld){
        this.name = name;
        this.meld = meld;
        for (Card c : meld){
            this.getChildren().add(StaticUtils.getShortToString(c));
        }
    }

    /**
     * adds card to meld
     * @param card card to be added
     */
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

    /**
     * resets meld (clears everything)
     */
    public void reset(){
        meld = new Hand();
        this.getChildren().clear();
    }
}
