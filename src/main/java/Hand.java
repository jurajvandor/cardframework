import java.util.ArrayList;

public class Hand extends GroupOfCards {

	private boolean visibility;

	public Hand(ArrayList<Card> cards) {
		super(cards);
	}

	public boolean getVisibility() {
		return this.visibility;
	}

	/**
	 * 
	 * @param visibility
	 */
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

}