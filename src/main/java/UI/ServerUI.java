package UI;

import Network.ServerConnectionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Juraj on 02.03.2017.
 */
public class ServerUI {
    public static void main(String[] args){
        System.out.println("Port number to connect:");
        int port, maxClients;
        try {
             port = Integer.parseInt((new BufferedReader(new InputStreamReader(System.in))).readLine());
        }
        catch (IOException e){
            System.out.println("Using default port 2222");
            port = 2222;
        }

        ServerConnectionListener connection = new ServerConnectionListener(port, 10);
        connection.start();
    }
}
