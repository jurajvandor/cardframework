package DataLayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable{

	private List<Player> players;
	public Game() {
		players = new ArrayList<>();
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
        }catch(IOException i) {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
        return game;
	}

}