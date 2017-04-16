package UI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Juraj on 11.04.2017.
 */
public class MeldView extends TextFlow{
    private String name;

    public MeldView(String name){
        this.name = name;
    }

    public void add(Text text){
        this.getChildren().add(text);
    }
}
