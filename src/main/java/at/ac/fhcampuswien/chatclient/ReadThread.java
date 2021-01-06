package at.ac.fhcampuswien.chatclient;

import at.ac.fhcampuswien.viewmodel.ChatController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private final ChatClient client;
    private static int counter = 0;

    public ReadThread(Socket socket, ChatClient client) {
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    String response;
    public void run() {
        while (true) {
            try {
                //System.out.println("ReadThread START: " + reader.readLine());
                response = reader.readLine();

                //Optional<String> response = Optional.ofNullable(reader.readLine());

                if(!response.contains("[Server]:") && response != null && !response.isEmpty()){
                    client.setResponseText(response);
                    System.out.println("ReadThread Client: " + client.getResponseText());
                } else {
                    //System.out.println("\n" + response);
                    System.out.println("ReadThread Server: " + response);
                }

                //client.setResponseText(response);


                //System.out.println("\n" + "ReadThread: "+ response);


                // prints the username after displaying the server's message
                /*if (client.getUsername() != null) {
                    System.out.print("[" + client.getUsername() + "]: ");
                }*/
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
