package cz.muni.fi.cardframework.Network;

import javafx.util.Pair;

/**
 * Created by Juraj Vandor on 05.03.2017.
 */

/**
 * static methods for parsing text messages of format ID TYPE other_message_text
 */
public class MessageParser {
    /**
     * gets ID from text which should be first "word" but if ID is not send -1 is returned instead
     * @param message text message to be parsed
     * @return pair of ID and other part of text
     */
    public static Pair<Integer, String> parseId(String message){
        int i = message.indexOf(' ');
        if(i == -1) return new Pair<>(-1, message);
        Integer id = Integer.parseInt(message.substring(0, i));
        String text = message.substring(i).trim();
        return new Pair<>(id,text);
    }

    /**
     * gets first word from text
     * @param message text to be parsed
     * @return pair of word (without space) and the rest of message (could be empty string)
     */
    public static Pair<String, String> parseType(String message){
        int i = message.indexOf(' ');
        if(i == -1) return new Pair<>(message, "");
        String type = message.substring(0, i);
        String text = message.substring(i).trim();
        return new Pair<>(type,text);
    }
}
