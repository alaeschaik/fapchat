package at.ac.fhcampuswien.chatclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private final Socket socket;
    private final ChatClient client;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        /*Scanner scanner = new Scanner(System.in);
        writer.println(client.getUsername());

        String text;

        do {
            System.out.print("[" + client.getUsername() + "]: ");
            text = scanner.next();
            writer.println(text);

        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }*/

        // TODO: 30/12/2020 Implement logic for chat.fxml 
    }
}
