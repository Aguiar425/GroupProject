import messages.Colors;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private static HashMap<String, Socket> clientMap = new HashMap<>();

    public static void main(String[] args) {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8080);

            while (true) {
                ExecutorService chat = Executors.newCachedThreadPool();
                final Socket clientSocket = serverSocket.accept();

                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeAndSend(clientSocket, Colors.YELLOW + "Please insert your username (" + Colors.RED + "EXIT" + Colors.YELLOW + " to leave the room, and " + Colors.PINK + "@+username" + Colors.YELLOW + " to send pm)" + Colors.RESET);

                String userName = consoleInput.readLine();
                broadcastMessage(Colors.BLUE + userName.concat(" has joined the server") + Colors.RESET);
                System.out.println(Colors.BLUE + userName.concat(" has joined the server" + Colors.RESET).toUpperCase());

                Thread t = new Thread(() -> {
                    try {
                        newClient(userName, clientSocket, clientMap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                chat.submit(t);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void newClient(String user, Socket socket, HashMap<String, Socket> clientMap) throws IOException {
        clientMap.put(user, socket);
        System.out.println(Colors.RED + clientMap + Colors.RESET);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msgReceived;

        while ((msgReceived = inputReader.readLine()) != null) {
            if (msgReceived.startsWith("@")) {

            } else {
                System.out.println("Message received from " + user.concat(": " + msgReceived));
                broadcastMessage(user + ": " + msgReceived);
            }
        }
        clientMap.remove(user, socket);
        broadcastMessage(Colors.YELLOW + user.concat(" has left the server").toUpperCase() + Colors.RESET);
        System.out.println(Colors.YELLOW + user.concat(" has left the server").toUpperCase() + Colors.RESET);
        System.out.println(Colors.RED + clientMap + Colors.RESET);
    }


    private static void broadcastMessage(String message) throws IOException {
        for (Map.Entry<String, Socket> characterName : clientMap.entrySet()) {
            writeAndSend(characterName.getValue(), message);
        }
    }

    private static void writeAndSend(Socket clientSocket, String message) throws IOException {
        BufferedWriter outputName = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        outputName.write(message);
        outputName.newLine();
        outputName.flush();
    }

}
