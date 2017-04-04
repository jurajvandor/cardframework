package UI;

import DataLayer.*;
import Network.CardframeworkListener;
import Network.Message;
import Network.MessageParser;
import Network.Server;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

/**
 * Created by Juraj Vandor on 02.03.2017.
 */
public class ServerUI implements CardframeworkListener, TurnAnnouncer {
    private Server connection;
    private Game game;
    private ServerTurnCounter turnCounter;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Port number to connect:");
        int port;
        try {
             port = Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine());
        }
        catch (IOException e){
            System.out.println("Using default port 2222");
            port = 2222;
        }

        ServerUI serverUI = new ServerUI();
        serverUI.connection = new Server(port, 10, serverUI);
        serverUI.connection.start();
        serverUI.game = new Game();
        serverUI.game.load(new XMLLoader(XMLLoader.class.getClassLoader().getResource("double_cards_with_4_jokers.xml").getPath()));
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
                if (game.getPlayers().size() == 4)
                    initiateGame();
                break;
            case "test":
                turnCounter.nextPlayerTurn();
                break;
            default:
                System.out.println("invalid message: " + message.getMessage());
        }
    }

    public void sendOtherNames(int id) {
        for (Integer p: game.getPlayers().keySet()) {
            if (p != id) connection.send(id, p + " CONNECTED " + game.getPlayer(p).getName());
        }
    }

    public void sendIds(){
        for (Integer p: game.getPlayers().keySet()) {
            connection.send(p, p + " YOUR_ID");
        }
    }

    public void closedConnection(){
        Set<Integer> closed = connection.getFreeIds();
        closed.retainAll(game.getPlayers().keySet());
        closed.forEach(x -> {
            game.removePlayer(x);
            connection.sendAllClients(x + " QUIT");
        });
    }

    public void initiateGame(){
        sendIds();
        Deck deck = game.createDeck("double french joker cards", game.getDesk(), "drawing");
        deck.shuffle();
        game.getDesk().addCards("discard", new Deck(DeckType.DISCARD));
        for (Player p : game.getPlayers().values()) {
            p.addCards("hand", new Hand());
            for (int i = 0; i < 4; i++){
                p.getCards("hand").addCard(deck.drawTopCard());
            }
        }
        connection.sendAllClients(new Message("GAME", game));
        List<Integer> ids = new ArrayList<>(game.getPlayers().keySet());
        ids.sort((x,y) -> (new Integer(x)).compareTo(y));
        turnCounter = new ServerTurnCounter(0, ids , this);
        turnCounter.nextPlayerTurn();
    }

    @Override
    public void announceTurn(int id) {
        connection.send(id, "YOUR_TURN");
    }
}
