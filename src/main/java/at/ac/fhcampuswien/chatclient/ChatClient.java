package at.ac.fhcampuswien.chatclient;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@Data
@Builder
public class ChatClient {
    private final String hostname;
    private final int port;
    private String username;
    private ReadThread readThread;
    private WriteThread writeThread;

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);

            System.out.println("Connected to the chat server");

            readThread = new ReadThread(socket, this);
            writeThread = new WriteThread(socket, this);
            readThread.start();
            writeThread.start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];

        ChatClient client = ChatClient.builder()
                                      .hostname(hostname)
                                      .port(port)
                                      .username(username)
                                      .build();
        client.execute();
    }
}