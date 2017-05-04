package UtilsUI;

import java.util.List;

/**
 * Created by Juraj Vandor on 31.03.2017.
 */
public class ServerTurnCounter {
    private int onTurnIndex;
    private TurnAnnouncer announcer;
    private List<Integer> ids;
    public ServerTurnCounter(int firstPlayerId, List<Integer> ids, TurnAnnouncer announcer){
        onTurnIndex = ids.indexOf(firstPlayerId) - 1;
        this.announcer = announcer;
        this.ids = ids;
    }

    public void resetAndNext(int firstPlayerId){
        onTurnIndex = ids.indexOf(firstPlayerId)-1;
        nextPlayerTurn();
    }

    public int getOnTurnId() {
        return ids.get(onTurnIndex);
    }

    public void removeIdFromGame(int id){
        ids.remove(id);
    }

    public void nextPlayerTurn(){
        do {
            onTurnIndex++;
            onTurnIndex %= ids.size();
        } while (ids.get(onTurnIndex) == null);
        announcer.announceTurn(ids.get(onTurnIndex));
    }

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
