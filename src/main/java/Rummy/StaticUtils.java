package Rummy;

import DataLayer.Card;
import DataLayer.Hand;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by Juraj Vandor on 11.04.2017.
 */
public class StaticUtils {
    public static int getValue(Card card){
        String value = card.getProperty("value");
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
        text.setFont(new Font(18));
        text.setText(res + " ");
        return text;
    }

    public static int getPoints(Card card){
        String value = card.getProperty("value");
        switch (value){
            case "A":
                return 1;
            case "J":
            case "Q":
            case "K":
                return 10;
            default:
                return Integer.parseInt(value);
        }
    }

    public static int getHandPoints(Hand hand){
        int sum = 0;
        for (Card c : hand){
            sum += getPoints(c);
        }
        return sum;
    }
}
