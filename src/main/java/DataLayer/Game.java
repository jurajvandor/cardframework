package DataLayer;

import java.io.*;
import java.util.*;

public class Game implements Serializable{

	private List<Player> players;
	private Map<String, LoadedCards> decks;
	public Game() {
		players = new ArrayList<>();
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

	public int addPlayer(String name){
		players.add(new Player(name));
		return players.size()-1;
	}

	public boolean removePlayer(int id){
		Player p = players.remove(id);
		return p != null;
	}

	public static boolean saveState(String filename, Game game) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
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