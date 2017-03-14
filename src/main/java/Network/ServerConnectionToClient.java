package Network;

import UI.FXListener;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
public class ServerConnectionToClient extends Thread implements Closeable {

    private Socket clientSocket = null;
    private final ServerConnectionToClient[] connections;
    private int maxClientsCount;
    private BlockingQueue<String> outputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ServerListener listener = null;
    private int id;
    private FXListener fxListener;

    public ServerConnectionToClient(Socket clientSocket, ServerConnectionToClient[] connections, int id, FXListener fxListener) {
        this.clientSocket = clientSocket;
        this.connections = connections;
        maxClientsCount = connections.length;
        this.id = id;
        this.fxListener = fxListener;
    }

    public void close(){
        listener.close();
        quit = true;
    }

    public void send(String message){
        outputBuffer.add(message);
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ServerConnectionToClient[] threads = this.connections;

        try {
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            PrintStream os = new PrintStream(clientSocket.getOutputStream());
            listener = new ServerListener( new BufferedReader(new InputStreamReader(is)), id, fxListener);
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()){
                    os.println(outputBuffer.take());
                }
                sleep(50);
            }

            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

