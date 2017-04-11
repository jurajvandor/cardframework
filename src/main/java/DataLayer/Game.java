package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

import java.io.*;
import java.util.*;

public class Game implements Serializable{

	private HashMap<Integer, Player> players;
	private HashMap<String, LoadedCards> decks;
	private Desk desk;
	public Game() {
		players = new HashMap<>();
		decks = new HashMap<>();
        desk = new Desk();
	}

	public void load(List<Loader> loaders){
        for (Loader l: loaders) {
            LoadedCards c = l.loadCards();
            decks.put(c.getName(), c);
        }
    }

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    public void load(Loader loader){
        LoadedCards c = loader.loadCards();
        decks.put(c.getName(), c);
	}

    public Deck createDeck(String nameOfCards){
	    return decks.get(nameOfCards).createDeck();
    }

    public Deck createDeck(String nameOfCards, CardOwner owner, String deckName){
        Deck deck = decks.get(nameOfCards).createDeck();
        owner.addCards(deckName, deck);
        return deck;
    }

    public void createDeckCustomizeName(String name, String customName, CardOwner owner){
        owner.addCards(customName, (decks.get(name).createDeck()));
    }

	public void addPlayer(int id, String name){
		players.put(id, new Player(name, id));
	}

	public Player getPlayer(int id){
	    return players.get(id);
    }

    public void removePlayer(int id) {
	    players.remove(id);
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

    public static void main(String[] args){//save and load testing
        Game game = new Game();
        game.addPlayer(0,"fero");
        game.load(new XMLLoader(XMLLoader.class.getClassLoader().getResource("french_cards.xml").getPath()));
        Deck deck = game.createDeck("french cards");
        TreeSet<Card> s = new TreeSet<>();
        s.add(deck.drawTopCard());
        game.getPlayer(0).addCards("ruka", new Hand(s));
        game.saveState("state.txt");
        Game game2 = Game.loadState("state.txt");
        game2.getPlayers();
    }
}