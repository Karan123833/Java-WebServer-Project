package org.example.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    private final ExecutorService threadPool;

    public void handleClient(Socket clientSocket) {
        try {
            System.out.println("Connection received from: " + clientSocket.getRemoteSocketAddress());
            System.out.println("Task assigned to thread: " + Thread.currentThread().getName());

            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String clientMessage = fromClient.readLine();
            System.out.println("   Client says: " + clientMessage);

            Thread.sleep(5000); // Simulate work

            toClient.println("Hello from the Thread Pool Server!");

            clientSocket.close();
            System.out.println("Task complete for: " + clientSocket.getRemoteSocketAddress());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Server() {
        int corePoolSize = 5;
        int maxPoolSize = 50;
        long keepAliveTime = 60;

        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadPoolExecutor.CallerRunsPolicy rejectionPolicy = new ThreadPoolExecutor.CallerRunsPolicy();

        this.threadPool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                rejectionPolicy
        );
        System.out.println("Thread Pool Server configured and ready.");
    }

    public void run() throws IOException {
        int port = 8010;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleClient(clientSocket));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
