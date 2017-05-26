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

/**
 * class that represents connection from client side
 */
public class ClientConnection extends Thread implements Closeable{


    private BlockingQueue<Message> outputBuffer = new LinkedBlockingQueue<>();
    private boolean quit = false;
    private ClientListener listener = null;
    private Socket clientSocket;
    private CardframeworkListener cardframeworkListener;
    private boolean fx;

    /**
     * connects to server with given parameters
     * @param host internet address of server
     * @param portNumber port that server is listening on
     * @param cardframeworkListener object that implements handling of incoming messages
     * @param fx true if JavaFX is used with direct handling of messages (Platform.runLater(..) is used for using methods from cardframeworkListener)
     * @throws IOException if something goes wrong
     */
    public ClientConnection(String host, int portNumber, CardframeworkListener cardframeworkListener, boolean fx) throws IOException{
        clientSocket = new Socket(host, portNumber);
        this.cardframeworkListener = cardframeworkListener;
        this.fx = fx;
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }});
    }

    /**
     * closes connection
     */
    public void close(){
        listener.close();
        quit = true;
    }

    /**
     * send message to server
     * @param message message to send
     */
    public void send(Message message){
        outputBuffer.add(message);
    }

    /**
     * sends message to server
     * @param message text message to send
     */
    public void send(String message){
        outputBuffer.add(new Message(message));
    }


    /**
     * initiates Diffie-Hellman key exchange and generates DES key, starts sending loop and listener
     * @throws NetworkLayerException if something goes wrong
     */
    public void run() throws NetworkLayerException {
        try {
            //generate DH keypair
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DiffieHellman");
            kpg.initialize(1024);
            KeyPair keys = kpg.generateKeyPair();
            //initiate streams
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(is);
            //exchange public keys
            os.writeObject(keys.getPublic());
            PublicKey otherKey = (PublicKey)ois.readObject();
            //combine recieved public and my private
            KeyAgreement agreement = KeyAgreement.getInstance("DH");
            agreement.init(keys.getPrivate());
            agreement.doPhase(otherKey, true);
            //generate DES key
            Key symKey = agreement.generateSecret("DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(ENCRYPT_MODE,symKey);
            //initiate sending and listening
            listener = new ClientListener( ois, cardframeworkListener, this, fx,symKey);
            listener.start();
            while (!quit){
                if (!outputBuffer.isEmpty()) {
                    os.reset();
                    try{
                        os.writeObject(new SealedObject(outputBuffer.take(), cipher));
                    }
                    catch (IllegalBlockSizeException e){
                        throw new NetworkLayerException(e);
                    }
                }
                os.flush();
                sleep(50);
            }
            //cleanup
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException | InterruptedException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new NetworkLayerException(e);
        }
        cardframeworkListener.closedConnection();
    }
}

