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
	private int id;

	/**
	 * creates card with given properties
	 * @param properties properties of card (they are beeing copied)
	 */
	public Card(Map<String,String> properties, int id){
		if (properties == null)
			throw new NullPointerException("properties is null");
		this.properties = new HashMap<>(properties);
		this.id = id;
	}

	/**
	 * copies card properties
	 * @param card card with the same properties
	 */
	public Card(Card card){
		this.properties = new HashMap<>(card.properties);
		this.id = card.id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		return id == card.id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}