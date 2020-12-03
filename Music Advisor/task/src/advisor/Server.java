package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static String code = "";
    private static HttpServer server;
    public static final String SERVER_URL = "http://localhost:233";

    public static void createServer() {

        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(233), 0);
            server.createContext("/", httpExchange -> {
                String query = httpExchange.getRequestURI().getQuery();
                if (query == null || !query.contains("code")) {
                    String failure = "Authorization code not found. Try again.";
                    httpExchange.sendResponseHeaders(200, failure.length());
                    httpExchange.getResponseBody().write(failure.getBytes());
                    httpExchange.getResponseBody().close();
                } else {
                    String success = "Got the code. Return back to your program.";
                    httpExchange.sendResponseHeaders(200, success.length());
                    httpExchange.getResponseBody().write(success.getBytes());
                    httpExchange.getResponseBody().close();
                    code = query.split("&")[0].split("=")[1];
                }
            });
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getCode() {
        return code;
    }

    public static void stopServer() {
        server.stop(1);
    }
}
