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

    public int getMeldCount(){
        return meldCount;
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
        if (deck.getTopCard() == null)
            Deck.swapAndTurnAround(game.getDesk().getDeck("discard"), game.getDesk().getDeck("drawing"));
        Card card = deck.drawTopCard();
        if (card != null) {
            game.getPlayer(id).getCards("hand").addCard(card);
            if (connection != null)
                connection.sendAllClients(id + " DRAW_CARD " + nameOfHand);
        }
    }

    public void addMeld(int id, Hand meld){
        game.getPlayer(id).getHand("hand").removeCards(meld);
        game.getDesk().addCards("meld" + meldCount, meld);
        meldCount++;
        if (connection != null)
            connection.sendAllClients(new Message(id + " MELD", meld));
    }

    public void layOff(int id, String meldName, Card card){
        game.getPlayer(id).getHand("hand").removeCard(card);
        game.getDesk().getCards(meldName).addCard(card);
        if (connection != null){
            connection.sendAllClients(new Message(id + " LAYOFF " + meldName, card));
        }
    }

    public void addPoints(int id, int score){
        int points = Integer.parseInt(game.getPlayer(id).getProperty("points"));
        game.getPlayer(id).addProperty("points", ((Integer)(points + score)).toString());
    }
}
