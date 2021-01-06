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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static java.util.concurrent.TimeUnit.*;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ChatController {

    private static int counter = 0;
    private List<String> messageList = new ArrayList<>();;
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

        DateTimeFormatter formatTimestamp = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.now();
        String currentTime = timestamp.format(formatTimestamp);

        StringBuilder myBuilder = new StringBuilder();
        myBuilder.append(currentTime + " " + ConnectionManager.client.getUsername() + ": " + messageTextField.getText());

        ConnectionManager.client.setSendText(myBuilder.toString());
        ConnectionManager.client.sendMessage(ConnectionManager.client.getSendText());

        String messageField = ConnectionManager.client.getResponseText();

        //messagePane.appendText(ConnectionManager.client.getSendText());
        System.out.println("ChatController: " + messageField);
        messageTextField.clear();

    }

    public void initialize(){

        ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();


        Runnable updateChat = () -> {
            Platform.runLater(()->{
                //System.out.println("UPDATE");
                if(ConnectionManager.client.getResponseText() != null && !ConnectionManager.client.getResponseText().contains("[Server]: ") && !ConnectionManager.client.getResponseText().isEmpty()) {
                    messagePane.appendText(ConnectionManager.client.getResponseText() + "\n");
                    ConnectionManager.client.setResponseText(null);
                }
            });
        };

        executor.scheduleAtFixedRate(updateChat, 0, 500, TimeUnit.MILLISECONDS);

    }




    //if(ConnectionManager.client.getResponseText() != null && !ConnectionManager.client.getResponseText().contains("[Server]: ") && !ConnectionManager.client.getResponseText().isEmpty())


    /*
    public void updateChat(){
        final Runnable updater = new Runnable() {
            @Override
            public void run() {
                messagePane.appendText(ConnectionManager.client.getResponseText());
                System.out.println("UPDATECHAT SUCCESSFULL");
            }
        };

        final ScheduledFuture<?> updateChatHandle =
                scheduler.scheduleAtFixedRate(updater, 10, 10, SECONDS);

        scheduler.schedule(new Runnable() {
            public void run() { updateChatHandle.cancel(true); }
        }, 60 * 60, SECONDS);
    }*/

    /*Platform.runLater(new Runnable() {
        public void run() {
            messagePane.appendText(ConnectionManager.client.getResponseText());
        }
    });*/


}
