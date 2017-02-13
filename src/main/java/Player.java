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
		// TODO - implement Player.addProperty
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param type
	 */
	public String getProperty(String type) {
		// TODO - implement Player.getProperty
		throw new UnsupportedOperationException();
	}

}