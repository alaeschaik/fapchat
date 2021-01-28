package at.ac.fhcampuswien.chatclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private final ChatClient client;


    public WriteThread(Socket socket, ChatClient client) {
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        //Sends String from ChatController, called by ChatClient

        if(client.getSendText() != null || client.getUsername() == client.getSendText()) {
            writer.println(client.getSendText());
            client.setSendText(null);
        } else {
            writer.println("statusUpdateRequest");
        }

    }


}
