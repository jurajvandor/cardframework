package UI;

import DataLayer.Card;
import DataLayer.Hand;

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

    public static String getShortToString(Card card){
        String res = card.getProperty("value");
        String suit = card.getProperty("suit");
        switch (suit){
            case "spades":
                res += "♠";
                break;
            case "hearts":
                res += "♥";
                break;
            case "diamonds":
                res += "♦";
                break;
            case "clubs":
                res += "♣";
                break;
        }
        return res;
    }

    public static String meldString(Hand hand){
        StringBuilder res = new StringBuilder();
        for (Card c : hand){
            res.append(getShortToString(c));
            res.append(" ");
        }
        return res.toString();
    }
}
