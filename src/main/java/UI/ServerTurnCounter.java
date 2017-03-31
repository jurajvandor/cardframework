package UI;

/**
 * Created by Juraj Vandor on 31.03.2017.
 */
public class ServerTurnCounter {
    private int onTurnId;
    private int numOfPlayers;
    private TurnAnnouncer announcer;
    public ServerTurnCounter(int firstPlayerId, int numOfPlayers, TurnAnnouncer announcer){
        onTurnId = firstPlayerId - 1;
        this.numOfPlayers = numOfPlayers;
        this.announcer = announcer;
    }

    public int getOnTurnId() {
        return onTurnId;
    }

    public void nextPlayerTurn(){
        onTurnId++;
        onTurnId %= numOfPlayers;
        announcer.announceTurn(onTurnId);
    }
    public void nextPlayerTurn(int numOfPlayersToJumpOver){
        onTurnId += numOfPlayersToJumpOver;
        onTurnId %= numOfPlayers;
        nextPlayerTurn();
    }
}
