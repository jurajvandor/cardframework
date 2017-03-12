package UI;

import Network.Server;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

/**
 * Created by Juraj on 02.03.2017.
 */
public class ServerUI {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Port number to connect:");
        int port, maxClients;
        try {
             port = Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine());
        }
        catch (IOException e){
            System.out.println("Using default port 2222");
            port = 2222;
        }

        Server connection = new Server(port, 10);
        connection.start();
        boolean connecting = true;
        while(connecting){
            if (connection.hasMessage()){
                Pair<Integer,String> message = connection.receivedMessage();

            }
            sleep(40);
        }
    }
}
