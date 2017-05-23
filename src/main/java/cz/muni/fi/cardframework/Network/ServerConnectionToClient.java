package cz.muni.fi.cardframework.Network;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static javax.crypto.Cipher.ENCRYPT_MODE;

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
    private KeyPair keys;

    public ServerConnectionToClient(Socket clientSocket, ServerConnectionToClient[] connections, int id, CardframeworkListener cardframeworkListener, KeyPair keys) {
        this.clientSocket = clientSocket;
        this.connections = connections;
        maxClientsCount = connections.length;
        this.id = id;
        this.cardframeworkListener = cardframeworkListener;
        this.keys = keys;
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }});
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
            ObjectInputStream ois = new ObjectInputStream(is);

            os.writeObject(keys.getPublic());
            PublicKey otherKey = (PublicKey)ois.readObject();

            KeyAgreement agreement = KeyAgreement.getInstance("DH");
            agreement.init(keys.getPrivate());
            agreement.doPhase(otherKey, true);

            Key symKey = agreement.generateSecret("DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(ENCRYPT_MODE,symKey);
            listener = new ServerListener( ois, id, cardframeworkListener, this, symKey);
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()){
                    os.reset();
                    try {
                        os.writeObject(new SealedObject(outputBuffer.take(), cipher));
                    }
                    catch (IllegalBlockSizeException e){
                        throw new NetworkLayerException(e);
                    }
                }
                sleep(50);
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException | InterruptedException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            throw new NetworkLayerException(e);
        }

        synchronized (connections) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }
        }
        cardframeworkListener.closedConnection();
    }
}

