package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Juraj Vandor on 22.02.2017.
 */
public class Server extends Thread implements Closeable{

    private ServerSocket serverSocket = null;
    private int portNumber;
    private int maxClientsCount;
    private ServerConnectionToClient[] threads;
    private boolean quit = false;
    private CardframeworkListener cardframeworkListener;

    public Server(int portNumber, int maxClientsCount, CardframeworkListener cardframeworkListener){
        this.portNumber = portNumber;
        this.maxClientsCount = maxClientsCount;
        this.threads = new ServerConnectionToClient[maxClientsCount];
        this.cardframeworkListener = cardframeworkListener;
    }

    public void quit(){
        quit = true;
    }

    public void close() {
        quit();
        for (ServerConnectionToClient c : threads){
            c.close();
        }
    }

    public void send(int id, String message){
        threads[id].send(message);
    }

    public void sendAllClients(String message){
        for (ServerConnectionToClient c : threads) {
            if (c != null) c.send(message);
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
                Socket clientSocket = serverSocket.accept();
                int i;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ServerConnectionToClient(clientSocket, threads, i, cardframeworkListener)).start();
                        break;
                    }
                }
                if (i == maxClientsCount || quit) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
