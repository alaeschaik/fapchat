package at.ac.fhcampuswien;

import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.setTitle("FAPChat");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                primaryStage.close();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
