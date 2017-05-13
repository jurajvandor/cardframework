package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

import java.io.*;
import java.util.*;

/**
 * represents single gameplay
 */
public class Game implements Serializable{

	private HashMap<Integer, Player> players;
	private HashMap<String, LoadedCards> decks;
	private Desk desk;

    /**
     * initiates values
     */
	public Game() {
		players = new HashMap<>();
		decks = new HashMap<>();
        desk = new Desk();
	}

    /**
     * loads list of loaders and creates loaded decks
     * @param loaders loaders to be loaded
     */
	public void load(Collection<Loader> loaders){
        for (Loader l: loaders) {
            LoadedCards c = l.loadCards();
            decks.put(c.getName(), c);
        }
    }

    /**
     * @return card owner - desk
     */
    public Desk getDesk() {
        return desk;
    }

    /**
     * sets new desk
     * @param desk new desk
     */
    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    /**
     * loades loader and creates loaded deck
     * @param loader loader to be loaded
     */
    public void load(Loader loader){
        LoadedCards c = loader.loadCards();
        decks.put(c.getName(), c);
	}

    /**
     * creates Deck from loaded cards under given name (is set while loading)
     * @param nameOfCards name of Deck to be created
     * @return created Deck
     */
    public Deck createDeck(String nameOfCards){
	    return decks.get(nameOfCards).createDeck();
    }

    /**
     * creates Deck from loaded cards under given name (is set while loading)
     * @param nameOfCards name of Deck to be created
     * @param owner future owner of created deck
     * @param deckName new name of deck to be stored at in card owner
     * @return created Deck
     */
    public Deck createDeck(String nameOfCards, CardOwner owner, String deckName){
        Deck deck = decks.get(nameOfCards).createDeck();
        owner.addCards(deckName, deck);
        return deck;
    }

    /**
     * adds new player to game
     * @param id id of player
     * @param name name of player
     */
	public void addPlayer(int id, String name){
		players.put(id, new Player(name, id));
	}

    public void addPlayers(Map<Integer,String> names){
	    for (int id : names.keySet()){
	        addPlayer(id,names.get(id));
        }
    }

    /**
     * puts player (rewrites) under given id
     * @param player player name
     */
	public void addPlayer(Player player) {
	    players.put(player.getId(), player);
    }

    /**
     * @param id id of player
     * @return player with this id
     */
	public Player getPlayer(int id){
	    return players.get(id);
    }

    /**
     * removes player from game
     * @param id id of player
     */
    public void removePlayer(int id) {
	    players.remove(id);
    }

    /**
     * @return unmodifiable collection of players
     */
    public Collection<Player>getPlayers(){
	    return Collections.unmodifiableCollection(players.values());
    }

    /**
     * @return unmodifiable collection of ids
     */
    public Collection<Integer>getIds() {
        return Collections.unmodifiableCollection(players.keySet());
    }

    /**
     * saves state of game to file
     * @param filename name of file with path
     * @return true if successful
     */
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

    /**
     * loades game from file
     * @param filename name of file with path
     * @return loaded game or null if not successful
     */
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