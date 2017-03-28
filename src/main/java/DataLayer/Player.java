package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import Network.ServerConnectionToClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Player extends CardOwner implements Serializable {

	private HashMap<String, String> properties;
	private String name;
	private int id;

	public Player(String name, int id) {
		this.properties = new HashMap<>();
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName(){
		return name;
	}

	public void addProperty(String type, String value) {
		properties.put(type,value);
	}

	public String getProperty(String type) {
		return properties.get(type);
	}

}