package DataLayer;

import java.io.Serializable;

public interface Loader extends Serializable {

	LoadedCards loadCards();

}