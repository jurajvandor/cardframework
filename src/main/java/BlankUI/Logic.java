package BlankUI;

import DataLayer.Game;
import Network.Server;

/**
 * Created by Juraj on 02.05.2017.
 */

/**
 * Holds logic of your game. Use if connection != null for sending informations to clients
 */
public class Logic {
    private Game game;
    private Server connection;

    /**
     * constructor for client
     * @param game Game object with data
     */
    public Logic(Game game){
        this.game = game;
        this.connection = null;
    }

    /**
     * constructor for server
     * @param game Game object with data
     * @param connection connection to clients
     */
    public Logic(Game game, Server connection){
        this.game = game;
        this.connection = connection;
    }

    public Game getGame() {
        return game;
    }
}
