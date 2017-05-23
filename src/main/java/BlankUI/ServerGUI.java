package BlankUI;/**
 * Created by Juraj on 18.05.2017.
 */

import DataLayer.Player;
import Network.NetworkLayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.BindException;

/**
 * Simple server gui which takes parameters from user and passes them to created ServerUI object
 */
public class ServerGUI extends Application {
    private ServerUI serverUI;
    private Label error;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Server");
        primaryStage.setResizable(false);
        primaryStage.setMaxWidth(300);
        primaryStage.setMaxHeight(400);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        primaryStage.setOnCloseRequest(event -> primaryStage.close());

        Label label1 = new Label("Port:");
        TextArea port = new TextArea("22222");
        port.setMaxHeight(10);
        Label label2 = new Label("Number of players:");
        TextArea numOfP = new TextArea("2");
        numOfP.setMaxHeight(10);
        Button button = new Button("Host");
        error = new Label("");

        VBox layout= new VBox(label1, port, label2, numOfP, button, error);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);

        primaryStage.show();
        button.setOnAction(event -> {
            int portNum = Integer.parseInt(port.getText());
            int players = Integer.parseInt(numOfP.getText());
            if (portNum < 65535 && players < 4) {//TODO change players
                Platform.runLater(() -> error.setText("initializing..."));
                serverUI = new ServerUI(portNum, players);
                Platform.runLater(() -> error.setText("listening..."));
            }
            else {
                error.setText("Invalid values");
            }
        });

        primaryStage.setOnCloseRequest(event -> {
                    new Thread(() -> {
                        serverUI.getConnection().quit();
                        serverUI.getConnection().close();
                    });
                    primaryStage.close();
                    System.exit(0);
                }

        );
    }


}
