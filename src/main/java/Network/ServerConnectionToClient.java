package Network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */
public class ServerConnectionToClient extends Thread implements Closeable {

    private Socket clientSocket = null;
    private final ServerConnectionToClient[] connections;
    private int maxClientsCount;
    private BlockingQueue<Message> outputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ServerListener listener = null;
    private int id;
    private CardframeworkListener cardframeworkListener;

    public ServerConnectionToClient(Socket clientSocket, ServerConnectionToClient[] connections, int id, CardframeworkListener cardframeworkListener) {
        this.clientSocket = clientSocket;
        this.connections = connections;
        maxClientsCount = connections.length;
        this.id = id;
        this.cardframeworkListener = cardframeworkListener;
    }

    public void close(){
        listener.close();
        quit = true;
    }

    public void send(String message){
        outputBuffer.add(new Message(message));
    }

    public void send(Message message){
        outputBuffer.add(message);
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ServerConnectionToClient[] threads = this.connections;

        try {
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            listener = new ServerListener( new ObjectInputStream(is), id, cardframeworkListener, this);
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()){
                    os.writeObject(outputBuffer.take());
                }
                sleep(50);
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }
        }
        cardframeworkListener.closedConnection();
    }
}


