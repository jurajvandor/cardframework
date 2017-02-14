import java.util.Set;
import java.util.TreeSet;

public class PlayerPropertyTypeValues implements Comparable<PlayerPropertyTypeValues>{

	private String type;
	private Set<String> values;


	public PlayerPropertyTypeValues(String type){
		this.type = type;
		values = new TreeSet<>();
	}

	public void addValue(String value){
		values.add(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlayerPropertyTypeValues that = (PlayerPropertyTypeValues) o;

		return type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	public int compareTo(PlayerPropertyTypeValues o) {
		if (this == o) return 0;
		return type.compareTo(o.type);
	}

}