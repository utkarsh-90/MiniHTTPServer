package server;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** Request/response logging (timestamp, client, method, path, status). */
public final class RequestLogger {

    private static final DateTimeFormatter ISO_FORMAT =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());

    private RequestLogger() {}

    public static void logRequest(String clientAddress, String method, String path, int statusCode) {
        String time = ISO_FORMAT.format(Instant.now());
        System.out.println(String.format("[%s] %s \"%s %s\" %d", time, clientAddress, method, path, statusCode));
    }

    public static void logError(String clientAddress, String method, String path, String message) {
        String time = ISO_FORMAT.format(Instant.now());
        System.err.println(String.format("[%s] %s \"%s %s\" ERROR: %s", time, clientAddress, method, path, message));
    }
}
