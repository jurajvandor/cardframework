package cz.muni.fi.cardframework.BlankUI;

import cz.muni.fi.cardframework.DataLayer.Game;
import cz.muni.fi.cardframework.DataLayer.XMLLoader;
import cz.muni.fi.cardframework.Network.CardframeworkListener;
import cz.muni.fi.cardframework.Network.Message;
import cz.muni.fi.cardframework.Network.MessageParser;
import cz.muni.fi.cardframework.Network.Server;
import cz.muni.fi.cardframework.UIUtils.TurnAnnouncer;
import javafx.util.Pair;

import java.util.Set;

import static java.lang.Thread.sleep;

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
    private Logic logic;

    /**
     * initiates values and starts listening
     * @param port port to listen on
     * @param numOfPlayers nnumber of players
     */
    public ServerUI(int port, int numOfPlayers)  {
        this.connection = new Server(port, 10, this);
        this.connection.start();
        this.game = new Game();
        this.logic = new Logic(this.game, this.connection);
        this.numOfPlayers = numOfPlayers;
        this.game.load(new XMLLoader("french_cards.xml"));//TODO
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
                connection.sendAllClients(message);
                sendOtherNames(id);
                if (game.getPlayers().size() == numOfPlayers)
                    initiateGame();
                break;
                //TODO
            default:
                System.out.println("invalid message: " + message.getMessage());
        }
        checkEnd();
    }

    /**
     * checks if game should end and if yes handles it.
     */
    public void checkEnd(){
        //TODO
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
     * creates new round of game with existing players
     */
    public void newPlay(){
        //TODO
    }

    /**
     * initiates whole game and creates new round
     */
    public void initiateGame(){
        sendIds();
        //TODO
        newPlay();
    }

    @Override
    public void announceTurn(int id) {
        connection.send(id, "YOUR_TURN");
    }
}
