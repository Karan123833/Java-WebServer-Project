package org.example.singlethreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException {
        int portnumber=8010;
        ServerSocket serverSocket = new ServerSocket(portnumber);
        System.out.println("Server is listening on port " + portnumber);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected from the " + clientSocket.getRemoteSocketAddress());

            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage = fromClient.readLine();

            System.out.println("Client says" + clientMessage);
            toClient.println("hELLO FROM THE server side hope you will enjoy");
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
