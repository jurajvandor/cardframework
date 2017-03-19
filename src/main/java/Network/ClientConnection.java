package Network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */
public class ClientConnection extends Thread implements Closeable{


    private BlockingQueue<String> outputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ClientListener listener = null;
    private Socket clientSocket;
    private CardframeworkListener cardframeworkListener;

    public ClientConnection(String host, int portNumber, CardframeworkListener cardframeworkListener) throws IOException{
        clientSocket = new Socket(host, portNumber);
        this.cardframeworkListener = cardframeworkListener;
    }

    public void close(){
        listener.close();
        quit = true;
    }

    public void send(String message){
        outputBuffer.add(message);
    }

    public void run() {
        try {
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            PrintStream os = new PrintStream(clientSocket.getOutputStream());
            listener = new ClientListener( new BufferedReader(new InputStreamReader(is)), cardframeworkListener, this);
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()) {
                    os.println(outputBuffer.take());
                }
                os.flush();
                sleep(50);
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        cardframeworkListener.closedConnection();
    }
}

