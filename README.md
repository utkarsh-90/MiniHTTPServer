# Mini HTTP Server

Plain Java HTTP server: TCP listener, thread pool, path routing, structured logging. No frameworks. Run locally.

## Requirements

- Java 17+
- Gradle 8+ (optional)

## Build & Run

**Gradle:**

```bash
gradle run
# or: gradle wrapper && chmod +x gradlew && ./gradlew run
```

**JDK only:**

```bash
mkdir -p build/classes/java/main
javac -d build/classes/java/main src/main/java/Main.java src/main/java/http/*.java src/main/java/server/*.java
java -cp build/classes/java/main Main
```

**Docker (local):**

```bash
docker build -t minijava-server .
docker run -p 8080:8080 minijava-server
```

Server listens on port 8080. Example:

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
| `src/main/java/Main.java` | Entry point; starts server on port 8080. |
| `src/main/java/http/` | `HttpRequest`, `HttpParser`, `HttpResponse`. |
| `src/main/java/server/` | `HttpServer`, `ClientHandler`, `Router`, `RequestLogger`. |

Extend routing in `Router.route()`: add path checks and return `RouteResult(statusCode, reasonPhrase, body)`.

## License

MIT. See [LICENSE](LICENSE).
