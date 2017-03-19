package UI;


import DataLayer.Game;
import DataLayer.XMLLoader;
import Network.CardframeworkListener;
import Network.ClientConnection;
import Network.MessageParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Created by Juraj Vandor on 05.03.2017.
 */
public class Controller implements CardframeworkListener {

    @FXML
    private TextField message;
    @FXML
    private TextArea chat;
    @FXML
    private GridPane gamepanel;
    private ClientConnection connection;
    private Game game;

    public Controller(){
        game = new Game();
        game.load(new XMLLoader(XMLLoader.class.getClassLoader().getResource("cards.xml").getPath()));
    }

    public Game getGame(){
        return game;
    }

    public void processMessage(String message){
        Pair<Integer,String> m = MessageParser.parseId(message);
        Pair<String, String> c = MessageParser.parseType(m.getValue());
        int id = m.getKey();
        String code = c.getKey();
        String text = c.getValue();
        switch (code){
            case "CHAT":
                chat.setText(chat.getText() + '\n' + game.getPlayer(id).getName() + ": " + text);
                break;
            case "NAME":
                game.addPlayer(id, text);
                chat.setText(chat.getText() + '\n' + text + " connected with id " + id +".");
                break;
            case "CONNECTED":
                game.addPlayer(id, text);
                System.out.println(message);
                break;
            default:
                System.out.println("invalid message: " + message);
        }
    }

    public void setConnection(ClientConnection connection){
        this.connection = connection;
    }

    public void handleMessage(){
        connection.send("CHAT " + message.getText());
        message.setText("");
    }
}
