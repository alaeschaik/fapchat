package at.ac.fhcampuswien.viewmodel;

import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    private static int counter = 0;
    private List<String> messageList = new ArrayList<>();;


    @FXML
    private ScrollPane messagePane;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    @FXML
    private Text onlineUserTextArea;

    @FXML
    public Group userOnline;

    @FXML
    public Text online1;

    @FXML
    private ImageView isOnlineAvatarImage;

    @FXML
    private TextField MessageTextField;

    @FXML
    private Button settingsButton;

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
    public void onSendButtonClicked(javafx.event.ActionEvent event) {
        StringBuilder myBuilder = new StringBuilder();
        messageList.add(messageTextField.getText());
        for(String msg  : messageList){
            myBuilder.append(ConnectionManager.client.getUsername() + ": " + msg + System.lineSeparator());
        }
        Text messagesField = new Text(myBuilder.toString());
        messagePane.setContent(messagesField);
        messageTextField.clear();

    }

}
