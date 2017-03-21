package DataLayer;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
public class Card implements Serializable {

	private HashMap<String, String> properties;

	public Card(HashMap<String,String> properties){
		this.properties = properties;
	}

	public Card(Card card){
		this.properties = new HashMap<>(card.properties);
	}

	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}
}