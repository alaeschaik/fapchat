package at.ac.fhcampuswien.viewmodel;

import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
    private Label settingsStatus;

    @FXML
    private void onApplyButtonClicked (ActionEvent event){
        if(!changeUsernameTextField.getText().isBlank()){
            ConnectionManager.client.setUsername(changeUsernameTextField.getText());
            settingsStatus.setTextFill(Color.web("#32CD32"));
            settingsStatus.setText("Dein Username wurde erfolgreich geaendert!!");
        }else{
            settingsStatus.setTextFill(Color.web("#8B0000"));
            settingsStatus.setText("Dein Username wurde nicht geaendert!");
            return;
        }

    }

    @FXML
    private void onCloseButtonClicked(MouseEvent event) {
        Parent root;

            try {
                root = FXMLLoader.load(getClass().getResource("/chat.fxml"));
                Stage stage = new Stage();
                stage.setTitle(String.format("%s - FAPChat", ConnectionManager.client.getUsername()));
                stage.setScene(new Scene(root, 600, 450));
                stage.show();
                // Hide this current window (if this is what you want)
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }


    @FXML
    private void onAvatarImageClicked(MouseEvent event) {
        //changeAvatarImage.setImage(ConnectionManager.client.getProfilePicture());
        /*
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 390, 600));
            stage.show();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                //Image selectedImage = new Image(selectedFile.getAbsolutePath());
                changeAvatarImage.setImage(new Image(ChatClient.class.getResource("resources/logo.png").toExternalForm()));
            }

            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/
    }
}
