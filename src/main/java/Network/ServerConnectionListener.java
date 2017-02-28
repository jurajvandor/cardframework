package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 22.02.2017.
 */
public class ServerConnectionListener implements Runnable{

    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private int portNumber;
    private int maxClientsCount;
    private ServerClientConnection[] threads = new ServerClientConnection[maxClientsCount];
    private BlockingQueue<ServerClientConnection> newClients  = new LinkedBlockingQueue<>();
    private boolean quit = false;

    ServerConnectionListener(int portNumber, int maxClientsCount){
        this.portNumber = portNumber;
        this.maxClientsCount = maxClientsCount;
    }

    public void quit(){
        quit = true;
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
                        (threads[i] = new ServerClientConnection(clientSocket, threads)).start();
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
