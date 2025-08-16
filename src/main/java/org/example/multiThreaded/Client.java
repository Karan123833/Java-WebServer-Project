package org.example.multiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private int clientId;

    public Client(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public void run() {
        String serverAddress = "localhost";
        int port = 8010;

        try {
            Socket socket = new Socket(serverAddress, port);

            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer.println("Hello from Client #" + clientId);

            String serverResponse = fromServer.readLine();
            System.out.println("Client #" + clientId + " received: " + serverResponse);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int numberOfClients = 5;
        System.out.println("Starting " + numberOfClients + " clients");

        for (int i = 1; i <= numberOfClients; i++) {
            Client clientTask = new Client(i);
            Thread clientThread = new Thread(clientTask);
            clientThread.start();
        }
    }
}
