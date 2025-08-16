package org.example.singlethreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public void run() throws IOException {
        String serverAddress = "localhost";
        int port = 8010;

        System.out.println("Trying to connect to " + serverAddress + " on port " + port);
        Socket socket = new Socket(serverAddress, port);
        System.out.println("✅ Successfully connected to the server!");

        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String messageToSend = "Hello World from the Client!";
        System.out.println("➡️  Sending message to server: " + messageToSend);
        toServer.println(messageToSend);

        String serverResponse = fromServer.readLine();
        System.out.println("⬅️  Response from server: " + serverResponse);

        toServer.close();
        fromServer.close();
        socket.close();
        System.out.println("Connection closed.");
    }

    public static void main(String[] args) {
        try{
            Client client = new Client();
            client.run();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
