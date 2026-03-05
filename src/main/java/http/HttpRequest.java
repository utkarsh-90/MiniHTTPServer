package http;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** HTTP request: method, path, version, headers. */
public class HttpRequest {

    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers;

    public HttpRequest(String method, String path, String version, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = new LinkedHashMap<>();
        if (headers != null) {
            this.headers.putAll(headers);
        }
    }

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getVersion() { return version; }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
