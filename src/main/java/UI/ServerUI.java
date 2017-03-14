package UI;

import Network.CardframeworkListener;
import Network.MessageParser;
import Network.Server;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

/**
 * Created by Juraj Vandor on 02.03.2017.
 */
public class ServerUI implements CardframeworkListener {
    private Server connection;
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
    }

    @Override
    public void processMessage(String message) {
        Pair<Integer,String> m = MessageParser.parseId(message);
        Pair<String, String> pair = MessageParser.parseType(m.getValue());
        System.out.println(m.getKey() + " messages: " + m.getValue());
        if (pair.getKey().equals("CHAT")) connection.sendAllClients("CHAT "+ m.getKey() + " messages: " + pair.getValue());
        else connection.sendAllClients(m.getKey() + " messages: " + m.getValue());
    }
}
