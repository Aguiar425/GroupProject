import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\033[34m";
    public static final String YELLOW = "\033[33m";
    public static final String PINK = "\u001B[35m";
    private static List<Socket> clientConnections = new ArrayList<>();
    private static HashMap<String, Socket> clientMap = new HashMap<>();
    private static int playerLimit;

    public static void main(String[] args) {

        ServerSocket serverSocket;
        int totalPlayers = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players?");
        while (true){
            try{
                playerLimit = Integer.parseInt(scanner.nextLine());
                break;
            }catch(NumberFormatException nfe){
                System.out.println("Please insert a valid number");
            }
        }
        try {
            serverSocket = new ServerSocket(8080);

            while (true) {
                ExecutorService chat = Executors.newCachedThreadPool();
                final Socket clientSocket = serverSocket.accept();

                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter outputName = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                printMainMenu();
                outputName.write(YELLOW + "What is your character's name?" + RESET);
                outputName.newLine();
                outputName.flush();
                String userName = consoleInput.readLine();
                totalPlayers++;

                if(totalPlayers < playerLimit){
                    broadcastMessage(BLUE + userName.concat(" has joined the party. Waiting for more players...") + RESET);
                    System.out.println(BLUE + userName.concat(" has joined the party" + RESET).toUpperCase());
                    Thread t = new Thread(() -> {
                        try {
                            newClient(userName, clientSocket, clientConnections, clientMap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    chat.submit(t);
                } else if (totalPlayers == playerLimit) {
                    broadcastMessage(BLUE + userName.concat(" has joined the party. The game is about to start") + RESET);
                    System.out.println(BLUE + userName.concat(" has joined the party" + RESET).toUpperCase());
                    System.out.println("Game starting");
                    Thread t = new Thread(() -> {
                        try {
                            newClient(userName, clientSocket, clientConnections, clientMap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    chat.submit(t);
                }else{
                    System.out.println("Player limit has been reached");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startGame() throws IOException {
        broadcastMessage("What is your character's class?");
    }

    private static void printMainMenu() throws IOException {
        Scanner input = new Scanner(new File("resources/ascii/mainMenu.txt"));

        while (input.hasNextLine())
        {
            broadcastMessage(input.nextLine().toString());
        }
    }


    private static void newClient(String user, Socket socket, List<Socket> socketList, HashMap<String, Socket> clientMap) throws IOException {
        socketList.add(socket);
        clientMap.put(user, socket);
        System.out.println(RED + socketList + RESET);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msgReceived;

        while ((msgReceived = inputReader.readLine()) != null) {
            if (msgReceived.startsWith("/")) {
                msgReceived = msgReceived.substring(1);

            } else {
                System.out.println("Message received from " + user.concat(": " + msgReceived));
                broadcastMessage(user + ": " + msgReceived);
            }
        }
        socketList.remove(socket);
        clientMap.remove(user, socket);
        broadcastMessage(YELLOW + user.concat(" has left the server").toUpperCase() + RESET);
        System.out.println(YELLOW + user.concat(" has left the server").toUpperCase() + RESET);
        System.out.println(RED + socketList + RESET);
    }


    private static void broadcastMessage(String message) throws IOException {
        for (Socket client : clientConnections) {
            BufferedWriter outPut = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            outPut.write(message);
            outPut.newLine();
            outPut.flush();
        }
    }

}
