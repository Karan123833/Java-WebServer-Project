package org.example.multiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void handleClient(Socket clientSocket) throws IOException {
        try {
            System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());
            System.out.println("Running on thread: " + Thread.currentThread().getName());

            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

           String clientMessage = fromClient.readLine();
           System.out.println("Client says: " + clientMessage);

           Thread.sleep(5000);
           toClient.println("Hello from the Multi-Threaded Server!");

            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        int port = 8010;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Multi-Threaded Server is listening on port: " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread clientHandlerThread = new Thread(() -> {
                try {
                    handleClient(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            clientHandlerThread.start();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
