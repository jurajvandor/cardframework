import java.io.File;
import java.util.Map;

public class Card {

	private Map<String, String> properties;

	public Card(Map<String,String> properties){
		this.properties = properties;
	}

	public Map<String, String> getProperties() {
		return this.properties;
	}

    public Card copyCard(){
	    return null;
    }
}