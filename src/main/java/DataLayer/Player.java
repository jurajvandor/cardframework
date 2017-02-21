package DataLayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Player extends CardOwner implements Serializable {

	private Map<String, String> properties;
	private Hand hand;



	private String name;

	public Player(String name) {
		this.properties = new HashMap<String, String>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addProperty(String type, String value) {
		properties.put(type,value);
	}

	public String getProperty(String type) {
		return properties.get(type);
	}

	public Hand getHand(){
		return hand;
	}

}