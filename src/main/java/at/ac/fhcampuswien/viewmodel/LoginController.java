package at.ac.fhcampuswien.viewmodel;

import at.ac.fhcampuswien.chatclient.ChatClient;
import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LoginController {

    @FXML
    private Label noServerFound;
    @FXML
    private Label invalidCredentials;
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
    private void onConnectButtonClicked(MouseEvent event) {
        invalidCredentials.setText("");
        noServerFound.setText("");
        if (hostnameTextField.getText().isBlank() || portTextField.getText().isBlank()) {
            noServerFound.setText("No Server found! Please enter a valid Server!");
        } else if (usernameTextField.getText().isBlank()) {
            invalidCredentials.setText("Invalid Credentials! Please enter a username!");
        } else {
            ConnectionManager.client = ChatClient.builder()
                    .hostname(hostnameTextField.getText())
                    .port(Integer.parseInt(portTextField.getText()))
                    .username(usernameTextField.getText())
                    .profilePicture(avatarImage.getImage())
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

    @FXML
    public void onProfilePictureClicked(MouseEvent event) throws IOException {
        fileChooser(avatarImage);
    }

    /*
     Static method so we can call it in other classes. Method creates a fileChooser and let the user choose a File (.png or .jpg)
    from his local machine and set's it for the current clients "profilePicture" variable
     */
    public static void fileChooser(ImageView imageToChange){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save ProfilePicture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);

        try (FileInputStream fis = new FileInputStream(file)) {
            Image image = new Image(fis);
            imageToChange.setImage(image);
            imageToChange.setFitWidth(160);
            imageToChange.setFitHeight(160);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
