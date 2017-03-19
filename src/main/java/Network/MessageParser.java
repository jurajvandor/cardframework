package Network;

import javafx.util.Pair;

/**
 * Created by Juraj Vandor on 05.03.2017.
 */
public class MessageParser {
    public static Pair<Integer, String> parseId(String message){
        int i = message.indexOf(' ');
        if(i == -1) return new Pair<>(-1, message);
        Integer id = Integer.parseInt(message.substring(0, i));
        String text = message.substring(i).trim();
        return new Pair<>(id,text);
    }

    public static Pair<String, String> parseType(String message){
        int i = message.indexOf(' ');
        if(i == -1) return new Pair<>(message, "");
        String type = message.substring(0, i);
        String text = message.substring(i).trim();
        return new Pair<>(type,text);
    }
}
