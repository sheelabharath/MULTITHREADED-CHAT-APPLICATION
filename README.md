# MULTITHREADED-CHAT-APPLICATION

COMPANY: CODTECH IT SOLUTIONS

NAME: SHEELA BHARATH TEJA REDDY

INTERN ID: CTIS8514

DOMAIN: JAVA

DURATION: 4 WEEKS

MENTOR: NEELA SANTOSH

DESCRIPTION:During my internship at CODTECH IT Solutions, I developed a Multithreaded Chat Application in Java as part of Task 3. The application follows a client-server architecture where a central server handles multiple clients simultaneously using Java's Socket, ServerSocket, and multithreading concepts. The server runs continuously on port 12345 and listens for incoming client connections in an infinite loop. Each time a new client connects, the server assigns a dedicated thread using the ClientHandler class which implements the Runnable interface, allowing multiple users to chat at the same time without blocking each other. When a client connects, they are first asked to enter their username, after which a join announcement is broadcast to all connected users. Every message typed by a client is received by the server and immediately broadcast to all other connected clients using a thread-safe synchronized list of PrintWriter objects. The client-side program runs two parallel operations — a background thread continuously listens for incoming messages from the server and prints them to the terminal, while the main thread reads user input and sends it to the server. Users can type /quit to gracefully disconnect from the chat, after which a leave announcement is broadcast to all remaining users. Key classes used include ServerSocket, Socket, BufferedReader, PrintWriter, InputStreamReader, Thread, and Collections.synchronizedList. The program was tested using three separate terminals — one for the server and two for individual clients — demonstrating real-time bidirectional communication. This task strengthened my understanding of Java networking, socket programming, and multithreading, which are core concepts in building real-world communication and distributed applications. The entire project was developed and tested in VS Code using JDK 22

#output: 

<img width="1472" height="984" alt="Image" src="https://github.com/user-attachments/assets/fe0c9d72-bf75-47f4-95a3-c18a8f8b399d" />
