package DataLayer;

import java.util.HashMap;
import java.util.Map;

public class Card {

	private Map<String, String> properties;

	public Card(Map<String,String> properties){
		this.properties = properties;
	}

	public Card(Card card){
		this.properties = new HashMap<>(card.properties);
	}

	public Map<String, String> getProperties() {
		return this.properties;
	}
}