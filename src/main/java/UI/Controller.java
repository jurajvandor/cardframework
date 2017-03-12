package UI;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Juraj on 05.03.2017.
 */
public class Controller {

    @FXML
    private TextField message;

    public void handleMessage(){
        System.out.println(message.getText());
        message.setText("");
    }
}
