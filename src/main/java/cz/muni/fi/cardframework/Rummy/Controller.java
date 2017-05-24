package cz.muni.fi.cardframework.Rummy;


import cz.muni.fi.cardframework.Network.CardframeworkListener;
import cz.muni.fi.cardframework.Network.ClientConnection;
import cz.muni.fi.cardframework.Network.Message;
import cz.muni.fi.cardframework.Network.MessageParser;
import cz.muni.fi.cardframework.DataLayer.*;
import cz.muni.fi.cardframework.UIUtils.PlayerActionHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by Juraj Vandor on 05.03.2017.
 */

/**
 * controls whole application and reacts to player's actions
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

    private MeldView currentMeldView;
    private MeldView layoffMeld;

    /**
     * initiates some values
     */
    public Controller(){
        game = new Game();
        state = GameState.NO_GAME;
        game.load(new XMLLoader("french_cards.xml"));
        currentMeldView = new MeldView("currentMeld", new Hand());
        layoffMeld = null;
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
        Scene connectionScene = new Scene(connectionLayout, 300, 400);
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
    public void winAnnouncement(boolean won) {
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

    @Override
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
                game = null;
                game  = (Game)message.getObject();
                state = GameState.WAITING;
                logic = new Logic(game);
                updateView();
                break;
            case "YOUR_ID":
                myId = id;
                break;
            case "YOUR_TURN":
                state = GameState.DRAW;
                addChatLine("Your Turn");
                break;
            case "DRAW_CARD":
                logic.drawCard(id, text);
                updateView();
                break;
            case "DISCARD":
                logic.discard(id, (Card)message.getObject());
                updateView();
                break;
            case "MELD":
                logic.addMeld(id, (Hand)message.getObject());
                updateView();
                break;
            case "LAYOFF":
                logic.layOff(id, text, (Card) message.getObject());
                updateView();
                break;
            case "GAME_END":
                logic.addPoints(id, (Integer)message.getObject());
                updateView();
                break;
            case "WINNER":
                winAnnouncement(true);
                break;
            case "LOOSER":
                winAnnouncement(false);
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
     * shows middle of gamePanel where 2 decks (drawing and discard) are shown with melds and button for melding
     */
    public void showDesk(){
        DeskView view = new DeskView(game.getDesk(), this);
        Button meld = new Button("Meld");
        HBox buttons = new HBox(meld, currentMeldView);
        meld.setOnAction(e -> {
            if (state == GameState.MELD){
                meld.setText("Meld");
                meldDone();
                state = GameState.DISCARD;
                return;
            }
            if (state == GameState.DISCARD) {
                meld.setText("Done");
                state = GameState.MELD;
            }
        });
        HBox melds = new HBox();
        for(int i = 0; i < logic.getMeldCount(); i++) {
            MeldView m = new MeldView("meld" + i, game.getDesk().getHand("meld"+i));
            m.setOnMouseClicked(e -> {
                if (state == GameState.LAY_OFF) {
                    layoffMeld = m;
                }
                if (state == GameState.DISCARD) {
                    layoffMeld = m;
                    state = GameState.LAY_OFF;
                }
            });
            melds.getChildren().add(m);
        }
        VBox desk = new VBox(melds, view, buttons);
        desk.setAlignment(Pos.CENTER);
        desk.setSpacing(10);
        gamePanel.setCenter(desk);
        view.show();
    }

    /**
     * shows each player of game (if hand is visible or not visible should be according to equality of yourId and player id)
     */
    public void showPlayers(){
        gamePanel.getChildren().clear();
        List<PlayerView> views = new ArrayList<>();
        List<Player> players = game.getPlayers().stream().sorted(
                (x,y) -> (new Integer(x.getId())).compareTo(y.getId())).collect(Collectors.toList());
        int meIndex = 0;
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getId() == myId) meIndex = i;
        }
        for (int i = meIndex; i < players.size()+meIndex; i++) {
            Player player = players.get(i%players.size());
            PlayerView view = new PlayerView(player.getId() == myId, player, this);
            views.add(view);
            view.show();
        }
        switch (views.size()){
            case 4:
                views.get(3).setRotate(90);
                gamePanel.setRight(views.get(3));
            case 3:
                gamePanel.setTop(views.get(2));
            case 2:
                views.get(1).setRotate(270);
                gamePanel.setLeft(views.get(1));
            case 1:
                gamePanel.setBottom(views.get(0));
        }
    }

    /**
     * calls showDesk and showPlayers. should be called after every transaction concerning object game
     */
    public void updateView() {
        showPlayers();
        showDesk();
    }

    /**
     * checks if meld has enough cards if yes sends it to server, else empties it
     */
    public void meldDone(){
        if (currentMeldView.getMeld().getCards().size() > 2) {
            connection.send(new Message("MELD", currentMeldView.getMeld()));
        }
        currentMeldView.reset();
    }

    /**
     * adds card to meld if criteria is met (first card is added without checking, every other
     * must be same value or same suit with value smaller/bigger than cards on edge of meld)
     * @param card card to be added
     * @param meldView view to be added to (could be while laying off or creating meld)
     * @return true if successful else false
     */
    public boolean addMeldingCard(Card card, MeldView meldView){
        boolean equals = true;
        for (Card c : meldView.getMeld()){
            equals = (equals && c.getProperty("value").equals(card.getProperty("value")));
        }
        if (equals){
            return true;
        }
        equals = true;
        int sum = 0;
        for (Card c : meldView.getMeld()){
                equals = (equals && c.getProperty("suit").equals(card.getProperty("suit")));
                sum += StaticUtils.getValue(c);
        }
        double avg = sum / (double)meldView.getMeld().size();
        double halfSize = (meldView.getMeld().size()+1) / 2.0;
        int cardValue = StaticUtils.getValue(card);
        boolean isNextCard = Math.abs(avg + halfSize - cardValue) < 0.01 || Math.abs(avg - halfSize - cardValue) < 0.01;
        return equals && isNextCard;
    }

    @Override
    public void handleCardClick(Card card, int playerId, String nameOfHand) {
        if (state == GameState.DRAW && playerId == -1) {
            Message m = new Message("DRAW_CARD " + nameOfHand);
            connection.send(m);
            state = GameState.DISCARD;
        }
        if (state == GameState.DISCARD && playerId == myId){
            Message m = new Message("DISCARD", card);
            connection.send(m);
            state = GameState.WAITING;
        }
        if (state == GameState.MELD && playerId == myId){
            if (addMeldingCard(card, currentMeldView))
                currentMeldView.add(card);
        }
        if (state == GameState.LAY_OFF && playerId == myId && layoffMeld != null){
            state = GameState.DISCARD;
            if (addMeldingCard(card, layoffMeld))
                connection.send(new Message("LAYOFF " + layoffMeld.getName(), card));
        }
    }
}
