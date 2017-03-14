package UI;

import Network.MessageParser;
import Network.Server;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

/**
 * Created by Juraj on 02.03.2017.
 */
public class ServerUI implements FXListener{
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
        boolean connecting = true;
        while(connecting){
            if (serverUI.connection.hasMessage()){
                Pair<Integer,String> message = serverUI.connection.receivedMessage();
                System.out.println(message.getKey() + " messages: " + message.getValue());
            }
            sleep(40);
        }
    }

    @Override
    public void processMessage(String message) {
        Pair<Integer,String> m = MessageParser.parseId(message);
        System.out.println(m.getKey() + " messages: " + m.getValue());
        connection.sendAllCliets(m.getKey() + " messages: " + m.getValue());
    }
}
