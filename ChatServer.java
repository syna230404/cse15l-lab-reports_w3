import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ChatServer {
    private static StringBuilder chatMessages = new StringBuilder();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(null, 8080);
        server.createContext("/add-message", new AddMessageHandler());
        server.setExecutor(null);
        server.start();
    }

    static class AddMessageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Extract parameters from the request URI
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");
            String user = params[1].substring(params[1].indexOf('=') + 1);
            String message = params[0].substring(params[0].indexOf('=') + 1);

            // Format the message and update chatMessages
            String formattedMessage = user + ": " + message + "\n";
            chatMessages.append(formattedMessage);

            // Send the response
            String response = chatMessages.toString();
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
}
