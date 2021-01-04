package at.ac.fhcampuswien.viewmodel;

import at.ac.fhcampuswien.chatclient.ChatClient;
import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField hostnameTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Button connectButton;

    @FXML
    private Button settingsButton;

    @FXML
    private ImageView avatarImage;


    @FXML
    private void onSettingsButtonClicked(MouseEvent event) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 390, 600));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onConnectButtonClicked(MouseEvent event) {
        ConnectionManager.client = ChatClient.builder()
                .hostname(hostnameTextField.getText())
                .port(Integer.parseInt(portTextField.getText()))
                .username(usernameTextField.getText())
                .build();
        ConnectionManager.client.execute();

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
}
