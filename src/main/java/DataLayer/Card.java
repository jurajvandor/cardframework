package DataLayer;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card  implements Serializable {

	private Map<String, String> properties;

	public Card(Map<String,String> properties){
		this.properties = properties;
	}

	public Card(Card card){
		this.properties = new HashMap<>(card.properties);
	}

	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}
}