import java.util.Set;
import java.util.TreeSet;

public class CardPropertyTypeValues implements Comparable<CardPropertyTypeValues>{

	private String type;
	private Set<String> values;

	public CardPropertyTypeValues(String type){
		this.type = type;
		values = new TreeSet<String>();
	}

	public void addValue(String value){
		values.add(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CardPropertyTypeValues that = (CardPropertyTypeValues) o;

		return type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	public int compareTo(CardPropertyTypeValues o) {
		if (this == o) return 0;
		return type.compareTo(o.type);
	}
}