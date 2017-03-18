package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import Network.ServerConnectionToClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Player extends CardOwner implements Serializable {

	private Map<String, String> properties;

	int id;

	public Player(int id) {
		this.properties = new HashMap<String, String>();
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void addProperty(String type, String value) {
		properties.put(type,value);
	}

	public String getProperty(String type) {
		return properties.get(type);
	}

}