package at.ac.fhcampuswien.chatclient;

import javafx.scene.image.Image;

public class ConnectionManager {

    public static ChatClient client;

    ConnectionManager(String hostname, int port, String username){
        client = ChatClient.builder()
                .hostname(hostname)
                .port(port)
                .profilePicture(new Image(ChatClient.class.getResource("resources/logo.png").toExternalForm()))
                .username(username)
                .build();
    }
}
