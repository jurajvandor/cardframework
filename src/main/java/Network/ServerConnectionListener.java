package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 22.02.2017.
 */
public class ServerConnectionListener extends Thread{

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private int portNumber;
    private int maxClientsCount;
    private ServerConnectionToClient[] threads;
    private BlockingQueue<ServerConnectionToClient> newClients  = new LinkedBlockingQueue<>();
    private boolean quit = false;

    public ServerConnectionListener(int portNumber, int maxClientsCount){
        this.portNumber = portNumber;
        this.maxClientsCount = maxClientsCount;
        this.threads = new ServerConnectionToClient[maxClientsCount];
    }

    public void quit(){
        quit = true;
    }

    public void sendAllCliets(String message){
        for (ServerConnectionToClient c : threads) {
            c.send(message);
        }
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!quit) {
            try {
                clientSocket = serverSocket.accept();
                int i;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ServerConnectionToClient(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    clientSocket.close();
                }
                else {
                    newClients.add(threads[i]);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        //add cleanup
    }
}
