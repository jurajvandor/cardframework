package UI;

import DataLayer.Card;
import DataLayer.Hand;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Juraj Vandor on 11.04.2017.
 */
public class StaticUtils {
    public static int getValue(Card card){
        String value = card.getProperty("value");
        System.out.println(value);
        switch (value){
            case "A":
                return 1;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            default:
                return Integer.parseInt(value);
        }
    }

    public static Text getShortToString(Card card){
        String res = card.getProperty("value");
        String suit = card.getProperty("suit");
        Text text = new Text();
        switch (suit){
            case "spades":
                res += "♠";
                break;
            case "hearts":
                res += "♥";
                text.setFill(Color.RED);
                break;
            case "diamonds":
                res += "♦";
                text.setFill(Color.RED);
                break;
            case "clubs":
                res += "♣";
                break;
        }
        text.setText(res + " ");
        return text;
    }

    public static MeldView meldString(Hand hand, String name){
        MeldView res = new MeldView(name);
        for (Card c : hand){
            res.add(getShortToString(c));
        }
        return res;
    }
}
