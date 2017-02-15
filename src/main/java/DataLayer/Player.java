package DataLayer;

import java.util.HashMap;
import java.util.Map;

public class Player extends CardOwner {

	private Map<String, String> properties;
	private Hand hand;

	public Player() {
		this.properties = new HashMap<String, String>();
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