package DataLayer;

import java.util.HashMap;
import java.util.Map;

public class Player extends CardOwner {

	private Map<String, String> properties;

	public Player() {
		this.properties = new HashMap<String, String>();
	}

	/**
	 * 
	 * @param type
	 * @param value
	 */
	public void addProperty(String type, String value) {
		properties.put(type,value);
	}

	/**
	 * 
	 * @param type
	 */
	public String getProperty(String type) {
		return properties.get(type);
	}

}