package UI;


import Network.CardframeworkListener;
import Network.ClientConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by Juraj on 05.03.2017.
 */
public class Controller implements CardframeworkListener {

    @FXML
    private TextField message;
    @FXML
    private TextArea chat;
    private ClientConnection connection;

    public void processMessage(String message){
        chat.setText(chat.getText() + '\n' + message);
    }

    public void setConnection(ClientConnection connection){
        this.connection = connection;
    }

    public void handleMessage(){
        connection.send(message.getText());
        message.setText("");
    }
}
