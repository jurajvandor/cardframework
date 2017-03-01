package Network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
public class ServerConnectionToClient extends Thread implements Closeable {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final ServerConnectionToClient[] connections;
    private int maxClientsCount;
    private BlockingQueue<String> outputBuffer = new LinkedBlockingQueue<>();
    private BlockingQueue<String> inputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ConnectionListener listener = null;

    public ServerConnectionToClient(Socket clientSocket, ServerConnectionToClient[] connections) {
        this.clientSocket = clientSocket;
        this.connections = connections;
        maxClientsCount = connections.length;
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
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            listener = new ConnectionListener(inputBuffer, new BufferedReader(new InputStreamReader(is)));
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()){
                    os.println(outputBuffer.take());
                }
                sleep(500);
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


