package at.ac.fhcampuswien.chatclient;

public class ConnectionManager {

    public static ChatClient client;

    ConnectionManager(String hostname, int port, String username){
        client = ChatClient.builder()
                .hostname(hostname)
                .port(port)
                .username(username)
                .build();
    }
}
