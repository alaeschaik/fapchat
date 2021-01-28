package at.ac.fhcampuswien.chatclient;


import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@Data
@Builder
public class ChatClient {

    private final String hostname;
    private final int port;
    private String username;
    private Image profilePicture;
    private ReadThread readThread;
    private WriteThread writeThread;
    private String sendText;
    private String responseText;
    private int userCounter;
    private String onlineUser;


    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);

            System.out.println("Connected to the chat server");

            readThread = new ReadThread(socket, this);
            writeThread = new WriteThread(socket, this);
            readThread.start();
            writeThread.start();

            //sends username after executing
            sendText = username;
            writeThread.run();

            //sends avatarImage after executing


        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }


    public void sendMessage(){
        //for sender immediately set responseText = sendText, because server does not broadcast back to sender (-->ReadThread)
        if(sendText != username && !sendText.startsWith(getUsername()))
            responseText = sendText;
        writeThread.run();

    }

    //updates onlineUser and onlineUserList
    public void statusUpdate(){
        writeThread.run();
    }

    public void end(){

        readThread.interrupt();
        writeThread.interrupt();

    }

}