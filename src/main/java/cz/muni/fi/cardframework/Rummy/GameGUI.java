package cz.muni.fi.cardframework.Rummy;
/**
 * Created by Juraj Vandor on 02.03.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * class that initiates controller and main window of client application
 */
public class GameGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RummyGUI.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Basic Rummy");
        primaryStage.setScene(new Scene(root, 1340, 680));
        primaryStage.setMinHeight(680);
        primaryStage.setMinWidth(1340);
        Controller controller = loader.getController();
        primaryStage.show();
        primaryStage.getScene().getRoot().setStyle("-fx-background: #FF9900;");
        controller.connectionWindow(primaryStage);
    }

    /**
     * just launches JavaFX app
     * @param args are passed to launch method
     */
    public static void main(String[] args) {
        launch(args);
    }

}

