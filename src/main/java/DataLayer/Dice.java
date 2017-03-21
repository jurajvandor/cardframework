package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import java.io.Serializable;
import java.util.Random;

public class Dice {

	private int min;
	private int max;
	private Random random;
	public Dice(int min, int max) {
		this.min = min;
		this.max = max;
		this.random = new Random();
	}

	public int roll() {
		return (random.nextInt()%max+1)+min;
	}

	public static int roll(int min, int max){
		return ((new Random()).nextInt()%max+1)+min;
	}
}