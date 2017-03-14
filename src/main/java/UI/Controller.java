package UI;


import Network.CardframeworkListener;
import Network.ClientConnection;
import Network.MessageParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by Juraj on 05.03.2017.
 */
public class Controller implements CardframeworkListener {

    @FXML
    private TextField message;
    @FXML
    private TextArea chat;
    @FXML
    private GridPane gamepanel;
    private ClientConnection connection;

    public void processMessage(String message){
        System.out.println(message);
        Pair<String,String> pair = MessageParser.parseType(message);
        if ( pair.getKey().equals( "CHAT") ) chat.setText(chat.getText() + '\n' + pair.getValue());
    }

    public void setConnection(ClientConnection connection){
        this.connection = connection;
    }

    public void handleMessage(){
        connection.send("CHAT " + message.getText());
        message.setText("");
    }
}
