package cz.muni.fi.cardframework.UIUtils;

/**
 * Created by Juraj on 31.03.2017.
 */

/**
 * interface used by ServerTurnAnnouncer class
 */
public interface TurnAnnouncer {
    /**
     * announces id of player that is on turn
     * @param id id of player on turn
     */
    void announceTurn(int id);
}
