package UI;

import DataLayer.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Juraj Vandor on 28.03.2017.
 */
public class PlayerView extends VBox{
    private boolean me;
    private Player player;
    private HandView hand;

    public PlayerView(boolean me, Player player, PlayerActionHandler handler) {
        this.me = me;
        this.player = player;
        hand = new HandView(player.getCards("hand"), "hand", player.getId(), me, handler);
        this.getChildren().add(new Label(player.getName()));
        this.getChildren().add(hand);
        this.setAlignment(Pos.CENTER);
    }

    public Player getPlayer() {
        return player;
    }

    public HandView getHand() {
        return hand;
    }

    public void show(){
        hand.show();
    }
}
