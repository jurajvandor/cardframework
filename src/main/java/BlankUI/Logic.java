package BlankUI;

import DataLayer.Game;
import Network.Server;

/**
 * Created by Juraj on 02.05.2017.
 */
public class Logic {
    private Game game;
    private Server connection;

    public Logic(Game game){
        this.game = game;
        this.connection = null;
    }

    public Logic(Game game, Server connection){
        this.game = game;
        this.connection = connection;
    }

    public Game getGame() {
        return game;
    }
}
