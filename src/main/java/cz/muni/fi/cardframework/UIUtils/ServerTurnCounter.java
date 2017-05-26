package cz.muni.fi.cardframework.UIUtils;

import java.util.List;

/**
 * Created by Juraj Vandor on 31.03.2017.
 */

/**
 * counter for player's turns
 */
public class ServerTurnCounter {
    private int onTurnIndex;
    private TurnAnnouncer announcer;
    private List<Integer> ids;

    /**
     * initiates counter
     * @param firstPlayerId id of first player on turn
     * @param ids list of all players ids (in which order their turn is)
     * @param announcer announcer of turn to client
     */
    public ServerTurnCounter(int firstPlayerId, List<Integer> ids, TurnAnnouncer announcer){
        onTurnIndex = ids.indexOf(firstPlayerId) - 1;
        this.announcer = announcer;
        this.ids = ids;
    }

    /**
     * resets turn counter to given player
     * @param firstPlayerId id of player
     */
    public void resetAndNext(int firstPlayerId){
        onTurnIndex = ids.indexOf(firstPlayerId)-1;
        nextPlayerTurn();
    }

    /**
     * @return last announced ID
     */
    public int getOnTurnId() {
        return ids.get(onTurnIndex);
    }

    /**
     * removed id form loop
     * @param id id to be removed
     */
    public void removeIdFromGame(int id){
        ids.remove(id);
    }

    /**
     * announces next id that is on turn (calls announcer.announceTurn())
     */
    public void nextPlayerTurn(){
        do {
            onTurnIndex++;
            onTurnIndex %= ids.size();
        } while (ids.get(onTurnIndex) == null);
        announcer.announceTurn(ids.get(onTurnIndex));
    }

    /**
     * announces next id that is on turn (calls announcer.announceTurn()),
     * given number of players is jumped over
     * @param numOfPlayersToJumpOver number of players that wont be on turn
     */
    public void nextPlayerTurn(int numOfPlayersToJumpOver){
        for (int i = 0; i < numOfPlayersToJumpOver; i++){
            do {
                onTurnIndex++;
                onTurnIndex %= ids.size();
            } while (ids.get(onTurnIndex) == null);
        }
        nextPlayerTurn();
    }
}
