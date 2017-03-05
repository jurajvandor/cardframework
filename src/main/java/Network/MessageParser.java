package Network;

import javafx.util.Pair;

/**
 * Created by Juraj on 05.03.2017.
 */
public class MessageParser {
    public static Pair<String, String> parse(String message){
        int i = message.indexOf(' ');
        String code = message.substring(0, i);
        String text = message.substring(i);
        return new Pair<>(code,text);
    }
}
