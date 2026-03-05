import server.HttpServer;

import java.io.IOException;

/** Entry point. Binds to PORT env or 8080. */
public class Main {

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null && !port.isEmpty()) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException ignored) { }
        }
        return 8080;
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer(getPort());
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
