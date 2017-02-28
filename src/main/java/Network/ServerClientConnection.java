package Network;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
public class ServerClientConnection extends Thread implements Closeable {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final ServerClientConnection[] connections;
    private int maxClientsCount;
    private BlockingQueue<String> outputBuffer = new LinkedBlockingQueue<>();
    private BlockingQueue<String> inputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ServerClientConnectionListener listener = null;

    public ServerClientConnection(Socket clientSocket, ServerClientConnection[] connections) {
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
        ServerClientConnection[] threads = this.connections;

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            listener = new ServerClientConnectionListener(inputBuffer, is);

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


