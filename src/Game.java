import messages.Colors;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private static HashMap<String, Socket> clientMap = new HashMap<>();
    private static int playerLimit;

    public static void main(String[] args) {

        ServerSocket serverSocket;
        int totalPlayers = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players?");
        while (true) {
            try {
                playerLimit = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Please insert a valid number");
            }
        }
        try {
            serverSocket = new ServerSocket(8080);

            while (true) {
                ExecutorService threadFactory = Executors.newCachedThreadPool();
                final Socket clientSocket = serverSocket.accept();

                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeAndSend(clientSocket, Colors.YELLOW + "What is your character's name?" + Colors.RESET);

                String userName = consoleInput.readLine();
                totalPlayers++;
                if (totalPlayers < playerLimit) {
                    broadcastMessage(Colors.BLUE + userName.concat(" has joined the party. Waiting for more players...") + Colors.RESET);
                    System.out.println(Colors.BLUE + userName.concat(" has joined the party" + Colors.RESET).toUpperCase());
                    threadFactory.submit(new Thread(() -> {
                        try {
                            playerThread(userName, clientSocket, clientMap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));
                } else if (totalPlayers == playerLimit) {
                    broadcastMessage(Colors.BLUE + userName.concat(" has joined the party. The game is about to start") + Colors.RESET);
                    System.out.println(Colors.BLUE + userName.concat(" has joined the party" + Colors.RESET).toUpperCase());
                    System.out.println("Game starting");
                    threadFactory.submit(new Thread(() -> {
                        try {
                            playerThread(userName, clientSocket, clientMap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }));

                } else {
                    System.out.println("Player limit has been reached");
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void playerThread(String user, Socket socket, HashMap<String, Socket> clientMap) throws IOException {
        clientMap.put(user, socket);
        System.out.println(Colors.RED + clientMap + Colors.RESET);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msgReceived;

        while ((msgReceived = inputReader.readLine()) != null) {
            if (msgReceived.startsWith("/")) {

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
