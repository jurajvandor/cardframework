package UI;


import Network.ClientConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Juraj on 05.03.2017.
 */
public class Controller {

    @FXML
    private TextField message;
    private ClientConnection connection;

    public void setConnection(ClientConnection connection){
        this.connection = connection;
    }

    public void handleMessage(){
        connection.send(message.getText());
        message.setText("");
    }
}
