package UI;

import DataLayer.Card;
import DataLayer.Deck;
import DataLayer.Game;
import Network.Message;
import Network.Server;

/**
 * Created by Juraj on 10.04.2017.
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

    public void setGame(Game game) {
        this.game = game;
    }

    public void discard(int id, Card card){
        game.getPlayer(id).getHand("hand").removeCard(card);
        game.getDesk().getCards("discard").addCard(card);
        if (connection != null)
            connection.sendAllClients(new Message(id + " DISCARD", card));
    }

    public void drawCard(int id, String nameOfHand){
        Deck deck = game.getDesk().getDeck(nameOfHand);
        Card card = deck.drawTopCard();
        if (card != null) {
            game.getPlayer(id).getCards("hand").addCard(card);
            if (connection != null)
                connection.sendAllClients(id + " DRAW_CARD " + nameOfHand);
        }
    }
}
