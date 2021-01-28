package at.ac.fhcampuswien.viewmodel;

import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ConnectionBuilder;


public class SettingsController {

    @FXML
    private Button closeButton;

    @FXML
    private Button applyButton;

    @FXML
    private TextField changeUsernameTextField;

    @FXML
    private ImageView changeAvatarImage;

    @FXML
    private Label settingsStatus;


    //changes the username if it is not the same and sends a message to all other clients
    @FXML
    private void onApplyButtonClicked (ActionEvent event){
        changeAvatarImage.setImage(ConnectionManager.client.getProfilePicture());
        if(!changeUsernameTextField.getText().isBlank() && !changeUsernameTextField.getText().equals(ConnectionManager.client.getUsername())){
            ConnectionManager.client.setSendText(ConnectionManager.client.getUsername() +" hat sich gerade in "+ changeUsernameTextField.getText() + " umbenannt!");
            ConnectionManager.client.sendMessage();
            ConnectionManager.client.setSendText(ConnectionManager.client.getUsername() + "CHANGEUSERNAME" + changeUsernameTextField.getText());
            ConnectionManager.client.sendMessage();
            ConnectionManager.client.setUsername(changeUsernameTextField.getText());
            settingsStatus.setTextFill(Color.web("#32CD32"));
            settingsStatus.setText("Dein Username wurde erfolgreich geaendert!!");
        }else{
            settingsStatus.setTextFill(Color.web("#8B0000"));
            settingsStatus.setText("Dein Username wurde nicht geaendert!");
        }
    }

    @FXML
    private void onCloseButtonClicked(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do, bye
        stage.close();
    }

    //When settings get opened to set the current ProfilePicture as the changeAvatarImage
    public void initialize() {
        this.changeAvatarImage.setImage(ConnectionManager.client.getProfilePicture());
    }

    /*
    Method to update the ProfilePicture over the Settings Window. Opens the "fileChooser" method from "LoginController" (method is static).
    Let you choose a Picture from your local machine and set it at new "profilePicture" attribute from the current user!
     */
    @FXML
    private void onAvatarImageClicked(MouseEvent event) {
        LoginController.fileChooser(changeAvatarImage);
        ConnectionManager.client.setProfilePicture(changeAvatarImage.getImage());
    }
}
