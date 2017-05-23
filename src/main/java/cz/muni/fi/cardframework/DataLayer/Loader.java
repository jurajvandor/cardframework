package cz.muni.fi.cardframework.DataLayer;
/**
 * Created by Juraj Vandor on 14.03.2017.
 */

import java.io.Serializable;

/**
 * interface for loading cards
 */
public interface Loader extends Serializable{

	/**
	 * loads cards to LoadedCards class which holds cards and their count in deck
	 * @return LoadedCards class with loaded cards
	 */
	LoadedCards loadCards();

}