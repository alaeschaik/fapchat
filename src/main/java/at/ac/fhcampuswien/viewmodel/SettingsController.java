package at.ac.fhcampuswien.viewmodel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class SettingsController {

    @FXML
    private Button applyButton;

    @FXML
    private TextField changeUsernameTextField;

    @FXML
    private ImageView changeAvatarImage;

    @FXML
    private void onApplyButtonClicked (MouseEvent event){

    }

    @FXML
    private void onCloseButtonClicked(MouseEvent event) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 400, 600));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}