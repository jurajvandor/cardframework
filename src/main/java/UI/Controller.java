package UI;


import DataLayer.*;
import Network.CardframeworkListener;
import Network.ClientConnection;
import Network.Message;
import Network.MessageParser;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashSet;

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

    public void closedConnection() {
        Stage s = (Stage) chat.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connection closed");
        alert.setHeaderText(null);
        alert.setContentText("Connection to server has been closed!");
        alert.showAndWait();
        s.close();
    }

    public void skuska(){
        Deck deck = game.createDeck("french cards");
        HashSet<Card> set = new HashSet<Card>();
        set.add(deck.drawTopCard());
        set.add(deck.drawTopCard());
        set.add(deck.drawTopCard());
        set.add(deck.drawTopCard());
        HandGUI h = new HandGUI(new Hand(set),"hand", 1, true);
        h.show();
        gamepanel.add(h,1,1);
    }

    public void addChatLine(String line){
        chat.setText(chat.getText() + '\n' + line);
    }

    public void processMessage(Message message){
        Pair<Integer,String> m = MessageParser.parseId(message.getMessage());
        Pair<String, String> c = MessageParser.parseType(m.getValue());
        int id = m.getKey();
        String code = c.getKey();
        String text = c.getValue();
        System.out.println(message.getMessage());
        switch (code){
            case "CHAT":
                addChatLine(game.getPlayer(id).getName() + ": " + text);
                break;
            case "NAME":
                game.addPlayer(id, text);
                addChatLine(text + " connected with id " + id +".");
                break;
            case "CONNECTED":
                game.addPlayer(id, text);
                break;
            case "QUIT":
                addChatLine(game.getPlayer(id).getName() + " disconnected.");
                game.removePlayer(id);
                break;
            default:
                System.out.println("invalid message: " + message.getMessage());
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
