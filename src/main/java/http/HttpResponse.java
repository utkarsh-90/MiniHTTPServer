package http;

import java.nio.charset.StandardCharsets;

/** HTTP response: status, headers, body. Content-Length is set from body bytes. */
public class HttpResponse {

    private final int statusCode;
    private final String reasonPhrase;
    private final String body;
    private final String contentType;

    public HttpResponse(int statusCode, String reasonPhrase, String body, String contentType) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.body = body != null ? body : "";
        this.contentType = contentType != null ? contentType : "text/plain; charset=utf-8";
    }

    public HttpResponse(int statusCode, String reasonPhrase, String body) {
        this(statusCode, reasonPhrase, body, "text/plain; charset=utf-8");
    }

    public int getStatusCode() { return statusCode; }
    public String getReasonPhrase() { return reasonPhrase; }
    public String getBody() { return body; }
    public String getContentType() { return contentType; }

    public byte[] getBodyBytes() {
        return body.getBytes(StandardCharsets.UTF_8);
    }

    /** Status line + headers + CRLF; body sent separately. */
    public String toHeaderString() {
        byte[] bodyBytes = getBodyBytes();
        return "HTTP/1.1 " + statusCode + " " + reasonPhrase + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
    }

    public String toFullResponseString() {
        return toHeaderString() + body;
    }
}
