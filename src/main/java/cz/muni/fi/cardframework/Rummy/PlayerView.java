package cz.muni.fi.cardframework.Rummy;

import cz.muni.fi.cardframework.DataLayer.Player;
import cz.muni.fi.cardframework.UIUtils.HandView;
import cz.muni.fi.cardframework.UIUtils.PlayerActionHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by Juraj Vandor on 28.03.2017.
 */

/**
 * graphical view for each player
 */
public class PlayerView extends VBox{
    private boolean me;
    private Player player;
    private HandView hand;

    /**
     * initiates values and creates the view
     * @param me true if cards should be visible
     * @param player player's data
     * @param handler handler for clicks on cards
     */
    public PlayerView(boolean me, Player player, PlayerActionHandler handler) {
        this.me = me;
        this.player = player;
        hand = new HandView(player.getCards("hand"), "hand", player.getId(), me, handler);
        Label label = new Label(player.getName() + ", " +player.getProperty("points") + "points");
        label.setFont(new Font(18));
        this.getChildren().add(label);
        this.getChildren().add(hand);
        this.setAlignment(Pos.CENTER);
    }

    public Player getPlayer() {
        return player;
    }

    public HandView getHand() {
        return hand;
    }

    /**
     * resets view of hand
     */
    public void show(){
        hand.show();
    }
}
