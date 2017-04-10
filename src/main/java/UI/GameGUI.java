package UI;
/**
 * Created by Juraj Vandor on 02.03.2017.
 */

import Network.ClientConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameGUI extends Application {

    private ClientConnection connection = null;
    private Controller controller;
    private Stage primaryStage;

    private boolean connect(String hostname, String port, String name){
        try {
            ClientConnection connection = new ClientConnection(hostname, Integer.parseInt(port), controller);
            connection.start();
            this.connection = connection;
            controller.setConnection(connection);
            connection.send("NAME " + name);
        }catch (IOException e){
            return false;
        }
        return true;
    }

    private void connectionWindow(){
        Stage connectStage = new Stage();
        connectStage.initModality(Modality.APPLICATION_MODAL);
        connectStage.setTitle("Connect");
        connectStage.setResizable(false);
        connectStage.setMaxWidth(300);
        connectStage.setMaxHeight(400);
        connectStage.setMinWidth(300);
        connectStage.setMinHeight(400);
        connectStage.setOnCloseRequest(event -> primaryStage.close());

        Label label1 = new Label("Hostname or IP adress:");
        TextArea hostname = new TextArea("localhost");
        hostname.setMaxHeight(10);
        Label label2 = new Label("Port:");
        TextArea port = new TextArea("2222");
        port.setMaxHeight(10);
        Label label3 = new Label("Player name:");
        TextArea name = new TextArea("Player");
        name.setMaxHeight(10);
        Button button = new Button("Connect");
        Label error = new Label("");

        VBox connectionLayout= new VBox(label1, hostname, label2, port, label3, name, button, error);
        connectionLayout.setSpacing(10);
        connectionLayout.setPadding(new Insets(10,10,10,10));
        Scene connectionScene = new Scene(connectionLayout);
        connectStage.setScene(connectionScene);

        connectStage.show();
        controller.skuska();
        button.setOnAction(event -> {
            error.setText("Connecting...");
            new Thread (() -> {
                if (connect(hostname.getText(), port.getText(), name.getText()))
                    Platform.runLater(() -> connectStage.close());
                else Platform.runLater(() -> error.setText("Could not connect to server"));
            }).start();
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGUI.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Defualt Game");
        primaryStage.setScene(new Scene(root, 1340, 680));
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1360);
        primaryStage.setOnCloseRequest(event -> {
            if (connection != null) connection.close();
        });
        controller = loader.getController();
        primaryStage.show();
        primaryStage.getScene().getRoot().setStyle("-fx-background: #FF9900;");
        connectionWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

