package DataLayer;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

/**
 * represents single playing card with its properties stored in map with String key and String value
 */
public class Card implements Serializable {

	private HashMap<String, String> properties;

	/**
	 * creates card with given properties
	 * @param properties properties of card (they are beeing copied)
	 */
	public Card(Map<String,String> properties){
		if (properties == null)
			throw new NullPointerException("properties is null");
		this.properties = new HashMap<>(properties);
	}

	/**
	 * copies card properties
	 * @param card card with the same properties
	 */
	public Card(Card card){
		this.properties = new HashMap<>(card.properties);
	}

	/**
	 * @param name key - type of property (health, attack, suit, value, etc..)
	 * @return property of card for given type, null if key does not exist
	 */
	public String getProperty(String name){
		return properties.get(name);
	}

	/**
	 * @return properties for cared in unmodifiable Map
	 */
	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}
}