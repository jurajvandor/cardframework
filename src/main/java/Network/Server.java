package Network;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Juraj Vandor on 22.02.2017.
 */
public class Server extends Thread implements Closeable{

    private KeyPair keys;
    private ServerSocket serverSocket = null;
    private int portNumber;
    private int maxClientsCount;
    private final ServerConnectionToClient[] threads;
    private boolean quit = false;
    private CardframeworkListener cardframeworkListener;

    public Server(int portNumber, int maxClientsCount, CardframeworkListener cardframeworkListener){

        this.portNumber = portNumber;
        this.maxClientsCount = maxClientsCount;
        this.threads = new ServerConnectionToClient[maxClientsCount];
        this.cardframeworkListener = cardframeworkListener;
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
            try {
                serverSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }});
    }

    public Set<Integer> getFreeIds(){
        Set<Integer> ids = new TreeSet<>();
        synchronized (threads) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == null) ids.add(i);
            }
        }
        return ids;
    }

    public void quit(){
        quit = true;
    }

    public void close() {
        quit();
        synchronized (threads) {
            for (ServerConnectionToClient c : threads) {
                try {
                    c.close();
                }catch (NetworkLayerException e){
                    e.printStackTrace();
                }
            }
        }
        try {
            serverSocket.close();
        }
        catch (IOException e){
            throw new NetworkLayerException(e);
        }
    }

    public void send(int id, String message){
        threads[id].send(message);
    }

    public void sendAllClients(String message){
        synchronized (threads) {
            for (ServerConnectionToClient c : threads) {
                if (c != null) c.send(message);
            }
        }
    }
    public void send(int id, Message message){
        synchronized (threads) {
            threads[id].send(message);
        }
    }

    public void sendAllClients(Message message){
        synchronized (threads) {
            for (ServerConnectionToClient c : threads) {
                if (c != null) c.send(message);
            }
        }
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException("server failed", e);
        }
        KeyPairGenerator gen = null;

        try {
             gen =  KeyPairGenerator.getInstance("DiffieHellman");
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        gen.initialize(1024);
        keys = gen.generateKeyPair();

        while (!quit) {
            try {
                Socket clientSocket = serverSocket.accept();
                int i;
                for (i = 0; i < maxClientsCount; i++) {
                    synchronized (threads) {
                        if (threads[i] == null) {
                            (threads[i] = new ServerConnectionToClient(clientSocket, threads, i, cardframeworkListener, keys)).start();
                            break;
                        }
                    }
                }
                if (i == maxClientsCount || quit) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                throw new NetworkLayerException(e);
            }
        }
    }
}
