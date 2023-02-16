import messages.Colors;
import messages.Messages;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private static HashMap<String, Socket> clientMap = new HashMap<>();
    private static int playerLimit;
    private static Game game;

    public static void main(String[] args) {
        game = new Game();
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
                writeAndSend(clientSocket, Colors.YELLOW + Messages.ASK_FOR_USERNAME + Colors.RESET);

                String userName = consoleInput.readLine();
                clientMap.put(userName, clientSocket);

                totalPlayers++;

                if (totalPlayers < playerLimit) {
                    broadcastMessage(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    System.out.println(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    createThread(threadFactory, clientSocket, userName);
                } else if (totalPlayers == playerLimit) {
                    broadcastMessage(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    broadcastMessage(Colors.BLUE + Messages.GAME_STARTED + Colors.RESET);

                    System.out.println(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    System.out.println(Messages.GAME_STARTED);

                    printMainMenu(); //TODO new game selection
                    broadcastMessage(game.startGame());

                    createThread(threadFactory, clientSocket, userName);

                } else {
                    System.out.println(Messages.PLAYER_LIMIT);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createThread(ExecutorService threadFactory, Socket clientSocket, String userName) {
        threadFactory.submit(new Thread(() -> {
            try {
                playerThread(userName, clientSocket, clientMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private static void printMainMenu() throws IOException {
        Path filePath = Path.of("resources/ascii/Game_Screens/mainMenu.txt");
        String content = Files.readString(filePath);
        broadcastMessage(content);
        //broadcastMessage(Colors.RED + "       <N>"+ Colors.RESET+"ew Game                 " + Colors.RED + "<L>"+ Colors.RESET + "oad Game");
        broadcastMessage(game.startGame());
        //BufferedReader input = new BufferedReader(new InputStreamReader(socket.GetInputStream()));
    }

    private static void playerThread(String user, Socket socket, HashMap<String, Socket> clientMap) throws IOException {
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
        //broadcastMessage(Colors.YELLOW + user.concat(" has left the server").toUpperCase() + Colors.RESET);
        System.out.printf(Colors.YELLOW + Messages.USER_LEFT.formatted(user) + Colors.RESET);
        broadcastMessage(Colors.YELLOW + Messages.USER_LEFT.formatted(user) + Colors.RESET);
        //System.out.println(Colors.YELLOW + user.concat(" has left the server").toUpperCase() + Colors.RESET);
        //System.out.println(Colors.RED + clientMap + Colors.RESET);
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