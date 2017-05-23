package BlankUI;


import DataLayer.*;
import Network.CardframeworkListener;
import Network.ClientConnection;
import Network.Message;
import Network.MessageParser;
import UIUtils.PlayerActionHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

/**
 * Created by Juraj Vandor on 05.03.2017.
 */

/**
 * controlls whole application and reacts to player's actions
 */
public class Controller implements CardframeworkListener, PlayerActionHandler {

    @FXML
    private TextField message;
    @FXML
    private TextArea chat;
    @FXML
    private BorderPane gamePanel;
    private ClientConnection connection;
    private Game game;
    private int myId;
    private Logic logic;

    private GameState state;


    /**
     * initiates some values
     */
    public Controller(){
        game = new Game();
        state = GameState.NO_GAME;
        game.load(new XMLLoader("french_cards.xml"));//TODO load another cards
    }

    /**
     * connects to server with given parameters and sets connection
     * @param hostname internet address
     * @param port port to which connection will be made
     * @param name name of player
     * @return
     */
    private boolean connect(String hostname, String port, String name){
        try {
            ClientConnection connection = new ClientConnection(hostname, Integer.parseInt(port), this, true);
            connection.start();
            this.connection = connection;
            connection.send("NAME " + name);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    /**
     * creates and shows connection window
     * @param primaryStage primary stage of game
     */
    public void connectionWindow(Stage primaryStage){
        primaryStage.setOnCloseRequest(event -> {
            if (connection != null) connection.close();
            System.exit(0);
        });
        Stage connectStage = new Stage();
        connectStage.initModality(Modality.APPLICATION_MODAL);
        connectStage.setTitle("Connect");
        connectStage.setResizable(false);
        connectStage.setMaxWidth(300);
        connectStage.setMaxHeight(400);
        connectStage.setMinWidth(300);
        connectStage.setMinHeight(400);
        connectStage.setOnCloseRequest(event -> primaryStage.close());

        Label label1 = new Label("Hostname or IP adress:");
        TextArea hostname = new TextArea("localhost");
        hostname.setMaxHeight(10);
        Label label2 = new Label("Port:");
        TextArea port = new TextArea("22222");
        port.setMaxHeight(10);
        Label label3 = new Label("Player name:");
        TextArea name = new TextArea("Player");
        name.setMaxHeight(10);
        Button button = new Button("Connect");
        Label error = new Label("");

        VBox connectionLayout= new VBox(label1, hostname, label2, port, label3, name, button, error);
        connectionLayout.setSpacing(10);
        connectionLayout.setPadding(new Insets(10,10,10,10));
        Scene connectionScene = new Scene(connectionLayout);
        connectStage.setScene(connectionScene);

        connectStage.show();
        button.setOnAction(event -> {
            error.setText("Connecting...");
            new Thread (() -> {
                if (connect(hostname.getText(), port.getText(), name.getText()))
                    Platform.runLater(() -> connectStage.close());
                else Platform.runLater(() -> error.setText("Could not connect to server"));
            }).start();
        });
    }

    @Override
    public void closedConnection() {
        Stage s = (Stage) chat.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connection closed");
        alert.setHeaderText(null);
        alert.setContentText("Connection to server has been closed!");
        alert.showAndWait();
        s.close();
    }

    /**
     * announcment shown when game ends
     * @param won true if player won else false
     */
    public void winAnnouncment(boolean won) {
        Stage s = (Stage) chat.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (won) {
            alert.setTitle("You Won");
            alert.setContentText("you won with " + game.getPlayer(myId).getProperty("points") + " points");
        } else{
            alert.setTitle("You Lost");
            alert.setContentText("you lost with " + game.getPlayer(myId).getProperty("points") + " points");
        }
        alert.showAndWait();
        game = new Game();
        state = GameState.NO_GAME;
        gamePanel.getChildren().clear();
        connectionWindow(s);
    }

    /**
     * adds line of text to chat
     * @param line string with text
     */
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
            case "GAME":
                addChatLine("Game begins.");
                game  = (Game)message.getObject();
                logic = new Logic(game);
                updateView();
                break;
            case "YOUR_ID":
                myId = id;
                break;
            case "WINNER":
                winAnnouncment(true);
                break;
            case "LOOSER":
                winAnnouncment(false);
                break;
            default:
                System.out.println("invalid message: " + message.getMessage());
        }
    }

    /**
     * handles new message written by player on this client (sends it to server)
     */
    public void handleMessage(){
        connection.send("CHAT " + message.getText());
        message.setText("");
    }

    /**
     * shows middle of gamePanel - Layout of desk according to object game
     */
    public void showDesk(){
        //TODO
    }

    /**
     * shows each player of game (if cards ar visible or not visible should be according to equality of yourId and player id
     */
    public void showPlayers(){
        //TODO code for 4 players can be found in Basic Rummy implementation
    }

    /**
     * calls showDesk and showPlayers. should be called after every transaction concerning object game
     */
    public void updateView() {
        showPlayers();
        showDesk();
    }

    @Override
    public void handleCardClick(Card card, int playerId, String nameOfHand) {
        //TODO
    }
}
