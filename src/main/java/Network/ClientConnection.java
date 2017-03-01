package Network;

import java.io.*;
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
    private ConnectionListener listener = null;
    private String host;
    private int portNumber;

    public ClientConnection(String host, int portNumber) {
        this.host = host;
        this.portNumber = portNumber;
    }

    public static void main(String[] args){

        ClientConnection c = new ClientConnection("localhost", 2223);
        c.start();
        c.send("hello");


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
            listener = new ConnectionListener(inputBuffer, new BufferedReader(new InputStreamReader(is)));
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()) {
                    os.println(outputBuffer.take());
                }
                os.flush();
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

