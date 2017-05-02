package UI;

import DataLayer.*;
import Network.CardframeworkListener;
import Network.Message;
import Network.MessageParser;
import Network.Server;
import javafx.print.PageLayout;
import javafx.util.Pair;
import sun.rmi.runtime.Log;

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
    private int numOfPlayers;
    private Server connection;
    private Game game;
    private Logic logic;
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
        serverUI.logic = new Logic(serverUI.game, serverUI.connection);
        serverUI.numOfPlayers = 4;
        serverUI.game.load(new XMLLoader(XMLLoader.class.getClassLoader().getResource("french_cards.xml").getPath()));
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

    public void checkEnd(){
        //TODO
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

    public void newPlay(){
        //TODO
    }

    public void initiateGame(){
        //TODO
        newPlay();
    }

    @Override
    public void announceTurn(int id) {
        connection.send(id, "YOUR_TURN");
    }
}