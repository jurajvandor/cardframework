package cz.muni.fi.cardframework.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Juraj Vandor on 22.02.2017.
 */

/**
 * class for creating server with listening to new connections from clients and receiving and sending new messages
 */
public class Server extends Thread implements Closeable{

    private KeyPair keys;
    private ServerSocket serverSocket = null;
    private int portNumber;
    private int maxClientsCount;
    private final ServerConnectionToClient[] threads;
    private boolean quit = false;
    private CardframeworkListener cardframeworkListener;

    /**
     * initiates server attributes
     * @param portNumber port to listen on
     * @param maxClientsCount maximum connected clients
     * @param cardframeworkListener message handler object
     */
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

    /**
     * @return ids that are not associated with any of clients
     */
    public Set<Integer> getFreeIds(){
        Set<Integer> ids = new TreeSet<>();
        synchronized (threads) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == null) ids.add(i);
            }
        }
        return ids;
    }

    /**
     * quits accepting new connections
     */
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

    /**
     * sends message to client
     * @param id id of client
     * @param message message to send
     */
    public void send(int id, String message){
        threads[id].send(message);
    }

    /**
     * sends message to all clients
     * @param message message to send
     */
    public void sendAllClients(String message){
        synchronized (threads) {
            for (ServerConnectionToClient c : threads) {
                if (c != null) c.send(message);
            }
        }
    }

    /**
     * sends message to client
     * @param id id of client
     * @param message message to send
     */
    public void send(int id, Message message){
        synchronized (threads) {
            threads[id].send(message);
        }
    }

    /**
     * sends message to all clients
     * @param message message to send
     */
    public void sendAllClients(Message message){
        synchronized (threads) {
            for (ServerConnectionToClient c : threads) {
                if (c != null) c.send(message);
            }
        }
    }

    /**
     * initiates socket and starts accepting incoming connections until quit() is called or socket is closed
     * also generates Diffie-Hellman keypair
     * @throws NetworkLayerException if something goes wrong
     */
    public void run() throws NetworkLayerException{
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException("server failed", e);
        }
        KeyPairGenerator gen = null;
        //generate DH keys
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
