package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** Parses request line and headers from a reader; does not read body. */
public class HttpParser {

    public HttpRequest parse(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            return null;
        }

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            throw new IOException("Malformed HTTP request line: " + requestLine);
        }

        String method = parts[0];
        String path = parts[1];
        String version = parts[2];
        Map<String, String> headers = new LinkedHashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) break;
            int colonIndex = line.indexOf(':');
            if (colonIndex <= 0) continue;
            String name = line.substring(0, colonIndex).trim();
            String value = line.substring(colonIndex + 1).trim();
            headers.put(name, value);
        }

        return new HttpRequest(method, path, version, headers);
    }
}
