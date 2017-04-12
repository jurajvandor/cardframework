package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

import java.io.Serializable;
import java.util.HashMap;

public class Player extends CardOwner implements Serializable {

	private HashMap<String, String> properties;
	private String name;
	private int id;

	/**
	 * creates player with empty properies
	 * @param name name of player
	 * @param id id of player
	 */
	public Player(String name, int id) {
		this.properties = new HashMap<>();
		this.name = name;
		this.id = id;
	}

	/**
	 * @return player id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return player name
	 */
	public String getName(){
		return name;
	}

	/**
	 * adds property to player (or overwrites one with same name)
	 * @param type name of property type
	 * @param value name of property
	 */
	public void addProperty(String type, String value) {
		properties.put(type,value);
	}

	/**
	 * @param type type of property (key)
 	 * @return value under given type (null if doesn't exist)
	 */
	public String getProperty(String type) {
		return properties.get(type);
	}

}