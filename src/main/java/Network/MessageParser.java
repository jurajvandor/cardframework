package Network;

import javafx.util.Pair;

/**
 * Created by Juraj on 05.03.2017.
 */
public class MessageParser {
    public static Pair<Integer, String> parseId(String message){
        int i = message.indexOf(' ');
        Integer id = Integer.parseInt(message.substring(0, i));
        String text = message.substring(i+1);
        return new Pair<>(id,text);
    }

    public static Pair<String, String> parseType(String message){
        int i = message.indexOf(' ');
        String type = message.substring(0, i);
        String text = message.substring(i+1);
        return new Pair<>(type,text);
    }
}
