package at.ac.fhcampuswien.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

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
        //reader permanently necessary --> while(true)
        while (true) {
            try {

                response = reader.readLine();

                //check if Message "from" Server or Client and if reader.readLine() null or empty
                if(!response.contains("[Server]:") && response != null && !response.isEmpty()){

                    //if Message from Client save es ResponseText for ChatClient
                    client.setResponseText(response);
                    System.out.println("ReadThread Client: " + client.getResponseText());

                } else {

                    //if Message not from client just Log in console
                    System.out.println("ReadThread Server: " + response);

                }

            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
