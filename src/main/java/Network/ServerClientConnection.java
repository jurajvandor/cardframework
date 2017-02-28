package Network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj on 28.02.2017.
 */
public class ServerClientConnection extends Thread {

    private String clientName = null;
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

    void quit(){
        listener.quit();
        quit = true;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ServerClientConnection[] threads = this.connections;

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            listener = new ServerClientConnectionListener(inputBuffer, is);

            while (!quit){
                if (!inputBuffer.isEmpty()){
                    os.println(inputBuffer.take());
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

class ServerClientConnectionListener extends Thread{
    private BlockingQueue<String> inputBuffer;
    private DataInputStream inputStream;
    private boolean quit = false;

    public void quit(){
        quit = true;
    }

    ServerClientConnectionListener(BlockingQueue<String> inputBuffer, DataInputStream inputStream){
        this.inputBuffer = inputBuffer;
        this.inputStream = inputStream;
    }

    public void run(){
        String message;
        try {
            while (!quit) {
                message = inputStream.readUTF();
                inputBuffer.add(message);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
