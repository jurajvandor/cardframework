package UI;


import DataLayer.*;
import Network.CardframeworkListener;
import Network.ClientConnection;
import Network.Message;
import Network.MessageParser;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Juraj Vandor on 05.03.2017.
 */
public class Controller implements CardframeworkListener, PlayerActionHandler{

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

    private PlayerView test;

    private List<PlayerView> players;

    private GameState state;

    private Hand currentMeld;
    private HBox currentMeldView;

    public Controller(){
        players = new ArrayList<>();
        game = new Game();
        state = GameState.NO_GAME;
        game.load(new XMLLoader(XMLLoader.class.getClassLoader().getResource("french_cards.xml").getPath()));
        currentMeld = null;
        currentMeldView = null;
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
        TreeSet<Card> set = new TreeSet<>();
        for (int i = 0; i< 12; i++)
            set.add(deck.drawTopCard());
        Player fero = new Player("fero", 4);
        fero.addCards("hand", new Hand(set));
        test = new PlayerView(true, fero, this);
        test.show();
        gamePanel.setBottom(test);
        PlayerView a = new PlayerView(false, fero, this);
        PlayerView b = new PlayerView(false, fero, this);
        a.show();
        PlayerView c = new PlayerView(false, fero, this);
        c.show();
        b.show();
        b.setRotate(270);
        c.setRotate(90);
        gamePanel.setTop(a);
        gamePanel.setLeft(b);
        gamePanel.setRight(c);
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
            case "GAME":
                addChatLine("Game begins.");
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
            case "UPDATE_PLAYER":
                game.addPlayer((Player) message.getObject());
                logic.setGame(game);
                updateView();
                break;
            case "UPDATE_DESK":
                game.setDesk((Desk) message.getObject());
                logic.setGame(game);
                updateView();
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

    public void showDesk(){
        DeskView view = new DeskView(game.getDesk(), this);
        Button meld = new Button("Meld");
        Button layoff = new Button("Lay-off");
        currentMeldView = new HBox();
        HBox buttons = new HBox(meld, layoff, currentMeldView);
        meld.setOnAction(e -> {
            if (state == GameState.MELD){
                meld.setText("Meld");
                meldDone();
                state = GameState.DISCARD;
                return;
            }
            if (state == GameState.DISCARD) {
                meld.setText("Done");
                currentMeld = new Hand();
                state = GameState.MELD;
            }
        });
        layoff.setOnAction(e -> state = GameState.LAY_OFF);
        HBox melds = new HBox();
        for(int i = 0; i < logic.getMeldCount(); i++)
            melds.getChildren().add(StaticUtils.meldString(game.getDesk().getHand("meld"+i), "meld"+i));
        VBox desk = new VBox(melds, view, buttons);
        desk.setAlignment(Pos.CENTER);
        desk.setSpacing(10);
        gamePanel.setCenter(desk);
        view.show();
    }

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

    public void updateView() {
        showPlayers();
        showDesk();
    }

    public void meldDone(){
        if (currentMeld.getCards().size() > 2) {
            connection.send(new Message("MELD", currentMeld));
        }
        currentMeld = null;
        currentMeldView.getChildren().clear();
    }

    public void addCurrentMeldCard(Card card){
        currentMeld.addCard(card);
        currentMeldView.getChildren().clear();
        currentMeldView.getChildren().add(StaticUtils.meldString(currentMeld, "currentMeld"));
    }

    public void addMeldingCard(Card card){
        boolean equals = true;
        for (Card c : currentMeld){
            equals = (equals && c.getProperty("value").equals(card.getProperty("value")));
        }
        if (equals){
            addCurrentMeldCard(card);
            return;
        }
        equals = true;
        int sum = 0;
        for (Card c : currentMeld){
                equals = (equals && c.getProperty("suit").equals(card.getProperty("suit")));
                sum += StaticUtils.getValue(c);
        }
        double avg = sum / (double)currentMeld.getCards().size();
        double halfSize = (currentMeld.getCards().size()+1) / 2.0;
        int cardValue = StaticUtils.getValue(card);
        boolean isNextCard = Math.abs(avg + halfSize - cardValue) < 0.01 || Math.abs(avg - halfSize - cardValue) < 0.01;
        if (equals && isNextCard) {
            addCurrentMeldCard(card);
        }
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
            addMeldingCard(card);
        }
        if (state == GameState.LAY_OFF && playerId == myId){
            addMeldingCard(card);
        }
    }
}
