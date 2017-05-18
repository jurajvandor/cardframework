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

public class ServerGUI extends Application {

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
        TextArea port = new TextArea("2222");
        port.setMaxHeight(10);
        Label label2 = new Label("Number of players:");
        TextArea numOfP = new TextArea("2");
        numOfP.setMaxHeight(10);
        Button button = new Button("Host");
        Label error = new Label("");

        VBox layout= new VBox(label1, port, label2, numOfP, button, error);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);

        primaryStage.show();
        button.setOnAction(event -> {
                Platform.runLater(() -> error.setText("initializing..."));
                    new ServerUI(Integer.parseInt(port.getText()), Integer.parseInt(numOfP.getText()));
                    Platform.runLater(() -> error.setText("listening..."));
        });
    }


}
