package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import java.util.Random;

/**
 * random generator
 */
public class Dice {

	private int min;
	private int max;
	private Random random;

	/**
	 * constructor with stable min and max value
	 * @param min min number to be generated
	 * @param max max number to be generated
	 */
	public Dice(int min, int max) {
		this.min = min;
		this.max = max;
		this.random = new Random();
	}

	/**
	 * generates new random number
	 * @return random number
	 */
	public int roll() {
		return (random.nextInt()%max+1)+min;
	}

	/**
	 * static function to generate random number
	 * @param min min number to be generated
	 * @param max max number to be generated
	 * @return random number
	 */
	public static int roll(int min, int max){
		return ((new Random()).nextInt()%max+1)+min;
	}
}