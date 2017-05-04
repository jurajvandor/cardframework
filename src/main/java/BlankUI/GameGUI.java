package BlankUI;
/**
 * Created by Juraj Vandor on 02.03.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

