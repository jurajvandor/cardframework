package Network;

import UI.FXListener;
import javafx.util.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 22.02.2017.
 */
public class Server extends Thread implements Closeable{

    private ServerSocket serverSocket = null;
    private int portNumber;
    private int maxClientsCount;
    private ServerConnectionToClient[] threads;
    private BlockingQueue<String> receivedMessages  = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private FXListener fxListener;

    public Server(int portNumber, int maxClientsCount, FXListener fxListener){
        this.portNumber = portNumber;
        this.maxClientsCount = maxClientsCount;
        this.threads = new ServerConnectionToClient[maxClientsCount];
        this.fxListener = fxListener;
    }

    public void quit(){
        quit = true;
    }

    public boolean hasMessage(){
        return !receivedMessages.isEmpty();
    }

    public Pair<Integer, String> receivedMessage(){
        String message = null;
        try{
            message = receivedMessages.take();
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
        if (message == null) return null;
        return MessageParser.parseId(message);
    }

    public void close() {
        quit();
        for (ServerConnectionToClient c : threads){
            c.close();
        }
    }

    public void sendAllCliets(String message){
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
                        (threads[i] = new ServerConnectionToClient(clientSocket, threads, i, fxListener)).start();
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
