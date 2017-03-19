package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Game implements Serializable{

	private Map<Integer, Player> players;
	private Map<String, LoadedCards> decks;
	private Desk desk;
	public Game() {
		players = new HashMap<>();
		decks = new HashMap<>();
	}

	public void load(List<Loader> loaders){
        for (Loader l: loaders) {
            LoadedCards c = l.loadCards();
            decks.put(c.getName(), c);
        }
    }

    public void load(Loader loader){
        LoadedCards c = loader.loadCards();
        decks.put(c.getName(), c);
	}

    public Deck createDeck(String name){
	    return decks.get(name).createDeck();
    }

    public void createDeck(String name, CardOwner owner){
        owner.addCards(name, (decks.get(name).createDeck()));
    }

    public void createDeckCustomizeName(String name, String customName, CardOwner owner){
        owner.addCards(customName, (decks.get(name).createDeck()));
    }

	public void addPlayer(int id, String name){
		players.put(id, new Player(name));
	}

	public Player getPlayer(int id){
	    return players.get(id);
    }

    public Map<Integer,Player>getPlayers(){
	    return Collections.unmodifiableMap(players);
    }

    public boolean saveState(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
            return false;
        }
        return true;
	}

	public static Game loadState(String filename) {
        Game game;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return game;
	}


}