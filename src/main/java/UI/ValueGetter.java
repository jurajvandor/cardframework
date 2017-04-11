package UI;

import DataLayer.Card;

/**
 * Created by Juraj Vandor on 11.04.2017.
 */
public class ValueGetter {
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
                return Integer.getInteger(value);
        }
    }
}
