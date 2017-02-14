import java.util.ArrayList;

public class Hand extends GroupOfCards {

	private boolean visibility;

	public Hand(ArrayList<Card> cards, boolean visibility) {
		super(cards);
		this.visibility = visibility;
	}

	public boolean getVisibility() {
		return this.visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

}