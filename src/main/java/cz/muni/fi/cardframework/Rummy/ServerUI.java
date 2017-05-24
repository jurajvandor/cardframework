package cz.muni.fi.cardframework.Rummy;

import cz.muni.fi.cardframework.Network.CardframeworkListener;
import cz.muni.fi.cardframework.Network.Message;
import cz.muni.fi.cardframework.Network.MessageParser;
import cz.muni.fi.cardframework.Network.Server;
import cz.muni.fi.cardframework.DataLayer.*;
import cz.muni.fi.cardframework.UIUtils.ServerTurnCounter;
import cz.muni.fi.cardframework.UIUtils.TurnAnnouncer;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by Juraj Vandor on 02.03.2017.
 */

/**
 * Class that handles server reactions on incoming messages, sends messages to clients, creates games and initiates them
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

    /**
     * initiates values and starts listening
     * @param port port to listen on
     * @param numOfPlayers nnumber of players
     * @param numberOfDeals number of deals until game ends
     */
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

    public Server getConnection() {
        return connection;
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

    /**
     * checks if game should end and if yes handles it by sending winner or looser to each client
     * or just by initiating new round.
     */
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

    /**
     * sends names of other players already connected apart from one given as parameter
     * @param id id to send to
     */
    public void sendOtherNames(int id) {
        for (Integer p: game.getIds()) {
            if (p != id) connection.send(id, p + " CONNECTED " + game.getPlayer(p).getName());
        }
    }

    /**
     * sends id to each client
     */
    public void sendIds(){
        for (Integer p: game.getIds()) {
            connection.send(p, p + " YOUR_ID");
        }
    }

    /**
     * handles closed connecton
     */
    public void closedConnection(){
        Set<Integer> closed = connection.getFreeIds();
        closed.retainAll(game.getIds());
        closed.forEach(x -> {
            game.removePlayer(x);
            connection.sendAllClients(x + " QUIT");
        });
    }

    /**
     * @return number of cards dealt to each player according to their count
     */
    private int getCardCount(){
        switch (numOfPlayers){
            case 2: return 10;
            case 3:
            case 4: return 7;
        }
        return 7;
    }

    /**
     * creates new round of game with existing players
     */
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

    /**
     * initiates whole game and creates new round
     */
    public void initiateGame(){
        sendIds();
        for (Player p : game.getPlayers())
            p.addProperty("points", "0");
        List<Integer> ids = new ArrayList<>(game.getIds());
        ids.sort((x,y) -> (new Integer(x)).compareTo(y));
        turnCounter = new ServerTurnCounter(0, ids , this);
        newPlay();
    }

    /**
     * @return list of ids that have highest score (wins)
     */
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
