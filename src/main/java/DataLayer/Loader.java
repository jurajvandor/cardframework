package DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */
import java.io.Serializable;

public interface Loader extends Serializable {

	LoadedCards loadCards();

}