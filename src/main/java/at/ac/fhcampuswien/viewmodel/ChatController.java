package at.ac.fhcampuswien.viewmodel;

import at.ac.fhcampuswien.chatclient.ChatClient;
import at.ac.fhcampuswien.chatclient.ConnectionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatController {

    private final List<String> messageList = new ArrayList<>();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    @FXML
    private TextArea messagePane;

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
    private Button disconnectButton;

    @FXML
    private void onSettingsButtonClicked(MouseEvent event) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 390, 600));
            stage.initOwner(settingsButton.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onSendButtonClicked(javafx.event.ActionEvent event) {
        ChatClient client = ConnectionManager.client;

        //Create and format Timestamp for Message
        DateTimeFormatter formatTimestamp = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.now();
        String currentTime = timestamp.format(formatTimestamp);

        //Format message
        String messageString = String.format("%s %s: %s", currentTime, client.getUsername(), messageTextField.getText());

        //Set client.SendText and call client.sendMessage()
        client.setSendText(messageString);
        client.sendMessage();
        //Log incoming Messages, always sendText from Sender, testing reasons
        System.out.println("ChatController: " + client.getResponseText());

        messageTextField.clear();
    }

    @FXML
    public void onDisconnectButtonClicked(MouseEvent event) throws IOException {

        ConnectionManager.client.setSendText("bye");
        ConnectionManager.client.sendMessage();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle(String.format("Login - FAPChat"));
            stage.setScene(new Scene(root, 400, 600));
            stage.show();

            // Hide this current window (if this is what you want)
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //FXMLLoader will now automatically call any suitably annotated no-arg initialize() method defined by the controller.
    //To update messagePane and userOnline we need initialize()
    public void initialize() {

        //Executor necessary for repeating updateChat
        ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();

        //Runnable updateChat has logic for updating messagePane
        Runnable updateChat = () -> {
            //Run the specific Runnable (updateChat) of the JavaFX App Thread at some unspecified time in the future.
            //Can be called from any Thread, will post the Runnable to an event queue and then return immediately to the caller.
            Platform.runLater(() -> {
                if(ConnectionManager.client.getResponseText() != null && !ConnectionManager.client.getResponseText().contains("[Server]: ") && !ConnectionManager.client.getResponseText().isEmpty()) {
                    messagePane.appendText(ConnectionManager.client.getResponseText() + "\n");
                    ConnectionManager.client.setResponseText(null);
                }
            });
        };

        //Updates OnlineUserStats
        Runnable updateUserOnline = () -> {
            Platform.runLater(()->{
                ConnectionManager.client.statusUpdate();
                onlineUserTextArea.setText("Online User: " + ConnectionManager.client.getUserCounter());
                System.out.println("UPDATE");
            });
        };

        //Specify time for update with executor
        executor.scheduleAtFixedRate(updateChat, 0, 500, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(updateUserOnline, 0, 2000, TimeUnit.MILLISECONDS);

    }

}
