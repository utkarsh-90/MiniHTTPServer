package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** TCP server: accepts connections on a port, dispatches to a fixed thread pool. */
public class HttpServer {

    private static final int THREAD_POOL_SIZE = 50;

    private final int port;
    private volatile boolean running = true;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public HttpServer(int port) {
        this.port = port;
    }

    /** Starts the server; blocks until stopped. */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                executor.submit(new ClientHandler(clientSocket));
            }
        }
    }

    /** Stops accepting new connections and shuts down the pool. */
    public void stop() {
        running = false;
        executor.shutdown();
    }
}
