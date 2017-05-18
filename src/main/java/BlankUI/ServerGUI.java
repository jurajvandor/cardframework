package BlankUI;/**
 * Created by Juraj on 18.05.2017.
 */

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

public class ServerGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void connectionWindow(Stage primaryStage){
        primaryStage.initModality(Modality.APPLICATION_MODAL);
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
        Button button = new Button("Connect");
        Label error = new Label("");

        VBox layout= new VBox(label1, port, label2, numOfP, button, error);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);

        primaryStage.show();
        button.setOnAction(event -> {
            new Thread (() -> {
                new ServerUI(Integer.parseInt(port.getText()), Integer.parseInt(numOfP.getText()));
                           }).start();
            error.setText("listening...");
        });
    }


    @Override
    public void start(Stage primaryStage) {

    }
}
