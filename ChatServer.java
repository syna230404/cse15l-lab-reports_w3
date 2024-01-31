import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

interface URLHandler {
    String handleRequest(URI url);
}

class ChatServerHandler implements URLHandler {
    private static StringBuilder chatMessages = new StringBuilder();

    @Override
    public String handleRequest(URI url) {
        // Extract parameters from the request URI
        String query = url.getQuery();
        String[] params = query.split("&");
        String user = params[1].substring(params[1].indexOf('=') + 1);
        String message = params[0].substring(params[0].indexOf('=') + 1);

        // Format the message and update chatMessages
        String formattedMessage = user + ": " + message + "\n";
        chatMessages.append(formattedMessage);

        // Return the entire chatMessages
        return chatMessages.toString();
    }
}

public class ChatServer {
    public static void main(String[] args) throws IOException {
        // Create an instance of ChatServerHandler
        ChatServerHandler chatServerHandler = new ChatServerHandler();

        // Start the server on port 8080 with the ChatServerHandler
        Server.start(8080, chatServerHandler);
    }
}
