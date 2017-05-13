package Network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Juraj Vandor on 28.02.2017.
 */
public class ClientConnection extends Thread implements Closeable{


    private BlockingQueue<Message> outputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ClientListener listener = null;
    private Socket clientSocket;
    private CardframeworkListener cardframeworkListener;
    private boolean fx;

    public ClientConnection(String host, int portNumber, CardframeworkListener cardframeworkListener, boolean fx) throws IOException{
        clientSocket = new Socket(host, portNumber);
        this.cardframeworkListener = cardframeworkListener;
        this.fx = fx;
    }

    public void close(){
        listener.close();
        quit = true;
    }

    public void send(Message message){
        outputBuffer.add(message);
    }

    public void send(String message){
        outputBuffer.add(new Message(message));
    }

    public void run() {
        try {
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            listener = new ClientListener( new ObjectInputStream(is), cardframeworkListener, this, fx);
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()) {
                    os.reset();
                    os.writeObject(outputBuffer.take());
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

