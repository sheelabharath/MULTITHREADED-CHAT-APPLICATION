import java.io.*;
import java.net.*;

public class ChatClient {

    private static final String SERVER_HOST = "localhost";
    private static final int    SERVER_PORT = 12345;

    // ─────────────────────────────────────────────
    // Inner thread: continuously reads messages from server
    // ─────────────────────────────────────────────
    static class MessageReceiver implements Runnable {
        private final BufferedReader serverIn;

        public MessageReceiver(BufferedReader serverIn) {
            this.serverIn = serverIn;
        }

        @Override
        public void run() {
            try {
                String message;
                // Keeps printing any message that arrives from the server
                while ((message = serverIn.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("[INFO] Disconnected from server.");
            }
        }
    }

    // ─────────────────────────────────────────────
    // MAIN – Connect and start chatting
    // ─────────────────────────────────────────────
    public static void main(String[] args) throws IOException {
        System.out.println("[INFO] Connecting to server at " + SERVER_HOST + ":" + SERVER_PORT);

        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        System.out.println("[INFO] Connected! Type your messages below. Use /quit to exit.\n");

        // Streams to communicate with the server
        BufferedReader serverIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter    serverOut = new PrintWriter(socket.getOutputStream(), true);

        // Start background thread to receive messages
        Thread receiver = new Thread(new MessageReceiver(serverIn));
        receiver.setDaemon(true);
        receiver.start();

        // Main thread reads user input and sends to server
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = userInput.readLine()) != null) {
            serverOut.println(line);      // send to server
            if (line.equalsIgnoreCase("/quit")) break;
        }

        socket.close();
        System.out.println("[INFO] You have left the chat.");
    }
}
