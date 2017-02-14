import java.util.Random;

public class Dice {

	private int min;
	private int max;
	public Dice(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int roll() {
		return ((new Random()).nextInt()%max+1)+min;
	}

	public static int roll(int min, int max){
		return ((new Random()).nextInt()%max+1)+min;
	}
}