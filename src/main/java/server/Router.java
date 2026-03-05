package server;

import http.HttpRequest;

/**
 * Path-based routing. Extend by adding path checks and returning {@link RouteResult}.
 * Unknown paths return 404.
 */
public class Router {

    public RouteResult route(HttpRequest request) {
        String path = request != null ? request.getPath() : null;

        if ("/".equals(path)) {
            return new RouteResult(200, "OK", "Welcome to Java HTTP Server\n");
        }
        if ("/hello".equals(path)) {
            return new RouteResult(200, "OK", "Hello API\n");
        }
        return new RouteResult(404, "Not Found", "404 Not Found\n");
    }

    public static class RouteResult {
        private final int statusCode;
        private final String reasonPhrase;
        private final String body;

        public RouteResult(int statusCode, String reasonPhrase, String body) {
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
            this.body = body;
        }

        public int getStatusCode() { return statusCode; }
        public String getReasonPhrase() { return reasonPhrase; }
        public String getBody() { return body; }
    }
}
