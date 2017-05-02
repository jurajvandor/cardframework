package UI;

import DataLayer.Game;
import Network.Server;

/**
 * Created by Juraj on 02.05.2017.
 */
public class Logic {
    private Game game;
    private Server connection;
    private int meldCount;

    public Logic(Game game){
        this.game = game;
        this.connection = null;
        this.meldCount = 0;
    }

    public Logic(Game game, Server connection){
        this.game = game;
        this.connection = connection;
        this.meldCount = 0;
    }

    public Game getGame() {
        return game;
    }
}
