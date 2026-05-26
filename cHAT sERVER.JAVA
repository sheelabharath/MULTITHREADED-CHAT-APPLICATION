import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    // Port the server listens on
    private static final int PORT = 12345;

    // Thread-safe list of all connected client writers
    private static final List<PrintWriter> clientWriters =
            Collections.synchronizedList(new ArrayList<>());

    // ─────────────────────────────────────────────
    // Broadcast a message to ALL connected clients
    // ─────────────────────────────────────────────
    public static void broadcast(String message) {
        System.out.println("[BROADCAST] " + message);
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);  // sends to each client's output stream
            }
        }
    }

    // ─────────────────────────────────────────────
    // Inner class: handles one client in its own thread
    // ─────────────────────────────────────────────
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Set up input/output streams for this client
                BufferedReader in  = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // First message from client is their chosen name
                out.println("[SERVER] Enter your name:");
                clientName = in.readLine();
                if (clientName == null || clientName.isBlank()) clientName = "User";

                // Register this client's writer so they receive broadcasts
                clientWriters.add(out);

                // Announce arrival
                broadcast("[SERVER] " + clientName + " has joined the chat!");

                // Keep reading messages until client disconnects
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) break;
                    broadcast(clientName + ": " + message);
                }

            } catch (IOException e) {
                System.out.println("[INFO] Client disconnected: " + clientName);
            } finally {
                // Clean up on disconnect
                if (out != null) clientWriters.remove(out);
                broadcast("[SERVER] " + clientName + " has left the chat.");
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }

    // ─────────────────────────────────────────────
    // MAIN – Start the server
    // ─────────────────────────────────────────────
    public static void main(String[] args) throws IOException {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   CODTECH Multithreaded Chat Server  ║");
        System.out.println("║   Listening on port " + PORT + "           ║");
        System.out.println("╚══════════════════════════════════════╝");

        ServerSocket serverSocket = new ServerSocket(PORT);

        // Accept clients in a loop – each gets its own thread
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[CONNECT] New client: " + clientSocket.getInetAddress());
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}
