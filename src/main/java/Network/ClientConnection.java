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
public class ClientConnection extends Thread implements Closeable{


    private BlockingQueue<String> outputBuffer = new LinkedBlockingQueue<>();
    private BlockingQueue<String> inputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ServerClientConnectionListener listener = null;
    private String host;
    private int portNumber;

    public ClientConnection(String host, int portNumber) {
        this.host = host;
        this.portNumber = portNumber;
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
            Socket clientSocket = new Socket(host, portNumber);
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            PrintStream os = new PrintStream(clientSocket.getOutputStream());
            listener = new ServerClientConnectionListener(inputBuffer, is);

            while (!quit){
                if (!outputBuffer.isEmpty()){
                    os.println(outputBuffer.take());
                }
                sleep(500);
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

