package server;

import http.HttpParser;
import http.HttpRequest;
import http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** Handles one client: parse request, route, send response, log. */
public class ClientHandler implements Runnable {

    private static final String UNKNOWN = "-";

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        String clientAddress = getClientAddress(clientSocket);
        try (Socket socket = this.clientSocket;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {

            HttpRequest request;
            try {
                request = new HttpParser().parse(reader);
            } catch (IOException e) {
                RequestLogger.logError(clientAddress, UNKNOWN, UNKNOWN, e.getMessage());
                sendErrorResponse(out, 400, "Bad Request", "Bad Request\n");
                return;
            }

            if (request == null) {
                RequestLogger.logError(clientAddress, UNKNOWN, UNKNOWN, "No request line");
                sendErrorResponse(out, 400, "Bad Request", "Bad Request\n");
                return;
            }

            int statusCode;
            try {
                Router.RouteResult result = new Router().route(request);
                statusCode = result.getStatusCode();
                HttpResponse response = new HttpResponse(
                        result.getStatusCode(),
                        result.getReasonPhrase(),
                        result.getBody()
                );
                out.write(response.toHeaderString().getBytes(StandardCharsets.UTF_8));
                out.write(response.getBodyBytes());
                out.flush();
            } catch (Exception e) {
                RequestLogger.logError(clientAddress, request.getMethod(), request.getPath(), e.getMessage());
                sendErrorResponse(out, 500, "Internal Server Error", "Internal Server Error\n");
                RequestLogger.logRequest(clientAddress, request.getMethod(), request.getPath(), 500);
                return;
            }

            RequestLogger.logRequest(clientAddress, request.getMethod(), request.getPath(), statusCode);
        } catch (IOException e) {
            RequestLogger.logError(clientAddress, UNKNOWN, UNKNOWN, e.getMessage());
        }
    }

    private static String getClientAddress(Socket socket) {
        if (socket == null || socket.getRemoteSocketAddress() == null) {
            return UNKNOWN;
        }
        return socket.getRemoteSocketAddress().toString();
    }

    private static void sendErrorResponse(OutputStream out, int statusCode, String reasonPhrase, String body) {
        try {
            HttpResponse response = new HttpResponse(statusCode, reasonPhrase, body);
            out.write(response.toHeaderString().getBytes(StandardCharsets.UTF_8));
            out.write(response.getBodyBytes());
            out.flush();
        } catch (IOException e) {
            RequestLogger.logError(UNKNOWN, UNKNOWN, UNKNOWN, "Failed to send error response: " + e.getMessage());
        }
    }
}
