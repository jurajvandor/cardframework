package Network;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static javax.crypto.Cipher.ENCRYPT_MODE;

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

    public void send(Message message){
        outputBuffer.add(message);
    }

    public void send(String message){
        outputBuffer.add(new Message(message));
    }


    public void run() throws NetworkLayerException {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DiffieHellman");
            kpg.initialize(1024);
            KeyPair keys = kpg.generateKeyPair();

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

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException | InterruptedException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            new NetworkLayerException(e);
        }
        cardframeworkListener.closedConnection();
    }
}

