package Rummy;

import UIUtils.*;
import DataLayer.*;
import Network.CardframeworkListener;
import Network.Message;
import Network.MessageParser;
import Network.Server;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Juraj Vandor on 02.03.2017.
 */
public class ServerUI implements CardframeworkListener, TurnAnnouncer {
    private int numOfPlayers;
    private Server connection;
    private Game game;
    private ServerTurnCounter turnCounter;
    private Logic logic;
    private boolean gameRunning = false;
    private int numberOfDeals;
    private int dealCounter = 0;
    private Map<Integer,String> names;

    public ServerUI(int port, int numOfPlayers, int numberOfDeals) {
        this.connection = new Server(port, 10, this);
        this.connection.start();

        this.names = new HashMap<>();
        this.numberOfDeals = numberOfDeals;
        this.numOfPlayers = numOfPlayers;
        this.game = new Game();
        this.game.load(new XMLLoader("french_cards.xml"));
        System.out.println("Server listening at port "+ port);
    }

    @Override
    public void processMessage(Message message) {
        Pair<Integer,String> m = MessageParser.parseId(message.getMessage());
        Pair<String, String> c = MessageParser.parseType(m.getValue());
        int id = m.getKey();
        String code = c.getKey();
        String text = c.getValue();
        switch (code){
            case "CHAT":
                connection.sendAllClients(message);
                break;
            case "NAME":
                game.addPlayer(id, text);
                names.put(id,text);
                connection.sendAllClients(message);
                sendOtherNames(id);
                if (game.getPlayers().size() == numOfPlayers)
                    initiateGame();
                break;
            case "DRAW_CARD":
                logic.drawCard(id, text);
                break;
            case "DISCARD":
                logic.discard(id, (Card)message.getObject());
                turnCounter.nextPlayerTurn();
                break;
            case "MELD":
                logic.addMeld(id, (Hand)message.getObject());
                break;
            case "LAYOFF":
                logic.layOff(id, text, (Card)message.getObject());
                break;
            default:
                System.out.println("invalid message: " + message.getMessage());
        }
        checkEnd();
    }

    public void checkEnd(){
        if (!gameRunning) return;
        Integer id = null;
        for (Player p : game.getPlayers()){
            if (p.getHand("hand").size() == 0)
                id = p.getId();
        }
        if (id == null) {
            return;
        }
        int sum = 0;
        for (Player p : game.getPlayers()){
            sum += StaticUtils.getHandPoints(p.getHand("hand"));
        }
        logic.addPoints(id,sum);
        connection.sendAllClients(new Message(id + " GAME_END", sum));
        if (numberOfDeals == dealCounter){
            List<Integer> winners = getWinners();
            for (Player p : game.getPlayers()){
                if (winners.contains(p.getId())){
                    connection.send(p.getId(), "WINNER");
                }
                else {
                    connection.send(p.getId(), "LOOSER");
                }
            }
            System.out.println("Game finished.");
        }
        else newPlay();
    }

    public void sendOtherNames(int id) {
        for (Integer p: game.getIds()) {
            if (p != id) connection.send(id, p + " CONNECTED " + game.getPlayer(p).getName());
        }
    }

    public void sendIds(){
        for (Integer p: game.getIds()) {
            connection.send(p, p + " YOUR_ID");
        }
    }

    public void closedConnection(){
        Set<Integer> closed = connection.getFreeIds();
        closed.retainAll(game.getIds());
        closed.forEach(x -> {
            game.removePlayer(x);
            connection.sendAllClients(x + " QUIT");
        });
    }

    private int getCardCount(){
        switch (numOfPlayers){
            case 2: return 10;
            case 3:
            case 4: return 7;
        }
        return 7;
    }

    public void newPlay(){
        game.setDesk(new Desk());
        Deck deck = game.createDeck("french cards", game.getDesk(), "drawing");
        deck.shuffle();
        game.getDesk().addCards("discard", new Deck(DeckType.DISCARD));
        for (Player p : game.getPlayers()) {
            p.addCards("hand", new Hand());
            int count = getCardCount();
            for (int i = 0; i < getCardCount(); i++){
                p.getCards("hand").addCard(deck.drawTopCard());
            }
        }
        logic = new Logic(game,connection);
        dealCounter++;
        gameRunning = true;
        connection.sendAllClients(new Message("GAME", game));
        turnCounter.resetAndNext(0);
    }

    public void initiateGame(){
        sendIds();
        for (Player p : game.getPlayers())
            p.addProperty("points", "0");
        List<Integer> ids = new ArrayList<>(game.getIds());
        ids.sort((x,y) -> (new Integer(x)).compareTo(y));
        turnCounter = new ServerTurnCounter(0, ids , this);
        newPlay();
    }

    public List<Integer> getWinners(){
        ArrayList<Integer> winners = new ArrayList<Integer>();
        int max = 0;
        for (Player p : game.getPlayers()){
            int points = Integer.parseInt(p.getProperty("points"));
            if (points > max){
                winners.clear();
                winners.add(p.getId());
                max = points;
            }else {
                if (points == max){
                    winners.add(p.getId());
                }
            }
        }
        return winners;
    }

    @Override
    public void announceTurn(int id) {
        connection.send(id, "YOUR_TURN");
    }
}
