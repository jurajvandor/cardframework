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

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGUI.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Defualt Game");
        primaryStage.setScene(new Scene(root, 1340, 680));
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1360);
        Controller controller = loader.getController();
        primaryStage.show();
        primaryStage.getScene().getRoot().setStyle("-fx-background: #FF9900;");
        controller.connectionWindow(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
