package at.ac.fhcampuswien;

import at.ac.fhcampuswien.viewmodel.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.setTitle("FAPChat");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();


        //TESTVERSUCHE
        ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
