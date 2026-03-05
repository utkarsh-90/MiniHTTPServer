# MiniJava HTTP Server

Plain Java HTTP server: TCP listener, thread pool, path routing, structured logging. No frameworks. Use as a library or run as-is.

## Requirements

- Java 17+
- Gradle 8+ (optional; see JDK-only build below)

## Build & Run

**Gradle:**

```bash
gradle run
# or, after: gradle wrapper && chmod +x gradlew
./gradlew run
```

**JDK only:**

```bash
mkdir -p build/classes/java/main
javac -d build/classes/java/main src/main/java/Main.java src/main/java/http/*.java src/main/java/server/*.java
java -cp build/classes/java/main Main
```

Server binds to `PORT` (default 8080). Example:

```bash
curl http://localhost:8080/
curl http://localhost:8080/hello
```

## API

| Method | Path    | Status | Body                          |
|--------|--------|--------|-------------------------------|
| GET    | `/`    | 200    | Welcome to Java HTTP Server   |
| GET    | `/hello` | 200  | Hello API                     |
| *      | other  | 404    | Not Found                     |

Responses: `Content-Type: text/plain; charset=utf-8`.

## Project layout

| Path | Purpose |
|------|---------|
| `src/main/java/Main.java` | Entry point; reads `PORT`, starts server. |
| `src/main/java/http/` | `HttpRequest`, `HttpParser`, `HttpResponse` — request/response types and parsing. |
| `src/main/java/server/` | `HttpServer` (listen + thread pool), `ClientHandler` (per-connection), `Router` (path → response), `RequestLogger`. |

Extend routing in `Router.route()`: add path checks and return `RouteResult(statusCode, reasonPhrase, body)`.

## Deploy

GitHub hosts code only. To expose the server publicly, deploy to a host that runs Java or Docker. See **[DEPLOY.md](DEPLOY.md)** for Render (free tier).

**Docker (local or any host):**

```bash
docker build -t minijava-server .
docker run -p 8080:8080 minijava-server
```

## Code style

- **Comments:** One-line class/method summary where useful. No long explanations; code should be readable without them.
- **Javadoc:** Only when the contract isn’t obvious (e.g. “returns null if stream ends before request line”).
- **Tone:** Direct and neutral. Prefer short sentences and standard terms.

## License

MIT. See [LICENSE](LICENSE).
