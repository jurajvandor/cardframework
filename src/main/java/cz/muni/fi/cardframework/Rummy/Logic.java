package cz.muni.fi.cardframework.Rummy;

import cz.muni.fi.cardframework.DataLayer.Card;
import cz.muni.fi.cardframework.DataLayer.Deck;
import cz.muni.fi.cardframework.DataLayer.Game;
import cz.muni.fi.cardframework.DataLayer.Hand;
import cz.muni.fi.cardframework.Network.Message;
import cz.muni.fi.cardframework.Network.Server;

/**
 * Created by Juraj on 10.04.2017.
 */

/**
 * holds logic for Basic Rummy
 */
public class Logic {
    private Game game;
    private Server connection;
    private int meldCount;

    /**
     * constructor for client
     * @param game Game object with data
     */
    public Logic(Game game){
        this.game = game;
        this.connection = null;
        this.meldCount = 0;
    }

    /**
     * constructor for server
     * @param game Game object with data
     * @param connection connection to clients
     */
    public Logic(Game game, Server connection){
        this.game = game;
        this.connection = connection;
        this.meldCount = 0;
    }

    public Game getGame() {
        return game;
    }

    public int getMeldCount(){
        return meldCount;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * discards card from players hand to discarding pile
     * @param id id of player
     * @param card card to be discarded
     */
    public void discard(int id, Card card){
        game.getPlayer(id).getHand("hand").removeCard(card);
        game.getDesk().getCards("discard").addCard(card);
        if (connection != null)
            connection.sendAllClients(new Message(id + " DISCARD", card));
    }

    /**
     * draws card from deck to players hand
     * @param id id of player
     * @param nameOfDeck name of deck (drawing or discard)
     */
    public void drawCard(int id, String nameOfDeck){
        Deck deck = game.getDesk().getDeck(nameOfDeck);
        if (deck.getTopCard() == null)
            Deck.swapAndTurnAround(game.getDesk().getDeck("discard"), game.getDesk().getDeck("drawing"));
        Card card = deck.drawTopCard();
        if (card != null) {
            game.getPlayer(id).getCards("hand").addCard(card);
            if (connection != null)
                connection.sendAllClients(id + " DRAW_CARD " + nameOfDeck);
        }
    }

    /**
     * adds completed meld to desk
     * @param id id of player
     * @param meld completed meld
     */
    public void addMeld(int id, Hand meld){
        game.getPlayer(id).getHand("hand").removeCards(meld);
        game.getDesk().addCards("meld" + meldCount, meld);
        meldCount++;
        if (connection != null)
            connection.sendAllClients(new Message(id + " MELD", meld));
    }

    /**
     * lays off card to existing meld
     * @param id id of player
     * @param meldName name of meld (meldX, where X is number of meld)
     * @param card card to be added
     */
    public void layOff(int id, String meldName, Card card){
        game.getPlayer(id).getHand("hand").removeCard(card);
        game.getDesk().getCards(meldName).addCard(card);
        if (connection != null){
            connection.sendAllClients(new Message(id + " LAYOFF " + meldName, card));
        }
    }

    /**
     * adds points to player
     * @param id id of player
     * @param score points to be added
     */
    public void addPoints(int id, int score){
        int points = Integer.parseInt(game.getPlayer(id).getProperty("points"));
        game.getPlayer(id).addProperty("points", ((Integer)(points + score)).toString());
    }
}
