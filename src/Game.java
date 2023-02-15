import messages.Colors;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private static List<Socket> clientConnections = new ArrayList<>();
    private static HashMap<String, Socket> clientMap = new HashMap<>();

    public static void main(String[] args) {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8080);
            //writeHistory("\nNEW SESSION");

            while (true) {
                ExecutorService chat = Executors.newCachedThreadPool();
                final Socket clientSocket = serverSocket.accept();

                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeAndSend(clientSocket, Colors.YELLOW + "Please insert your username (" + Colors.RED + "EXIT" + Colors.YELLOW + " to leave the room, and " + Colors.PINK + "@+username" + Colors.YELLOW + " to send pm)" + Colors.RESET);

                String userName = consoleInput.readLine();
                broadcastMessage(Colors.BLUE + userName.concat(" has joined the server") + Colors.RESET);
                System.out.println(Colors.BLUE + userName.concat(" has joined the server" + Colors.RESET).toUpperCase());
                //writeHistory(userName.concat(" has joined the server").toUpperCase());


                Thread t = new Thread(() -> {
                    try {
                        newClient(userName, clientSocket, clientConnections, clientMap);
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


    private static void newClient(String user, Socket socket, List<Socket> socketList, HashMap<String, Socket> clientMap) throws IOException {
        socketList.add(socket);
        clientMap.put(user, socket);
        System.out.println(Colors.RED + socketList + Colors.RESET);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msgReceived;

        while ((msgReceived = inputReader.readLine()) != null) {
            if (msgReceived.startsWith("@")) {
                msgReceived = msgReceived.substring(1);
                //privateMessage(socket, clientMap, msgReceived, user);

            } else {
                System.out.println("Message received from " + user.concat(": " + msgReceived));
                broadcastMessage(user + ": " + msgReceived);
                //writeHistory(user.concat(": " + msgReceived));
            }
        }
        socketList.remove(socket);
        clientMap.remove(user, socket);
        broadcastMessage(Colors.YELLOW + user.concat(" has left the server").toUpperCase() + Colors.RESET);
        //writeHistory(user.concat(" has left the server").toUpperCase());
        System.out.println(Colors.YELLOW + user.concat(" has left the server").toUpperCase() + Colors.RESET);
        System.out.println(Colors.RED + socketList + Colors.RESET);
    }


    private static void broadcastMessage(String message) throws IOException {
        for (Socket client : clientConnections) {
            writeAndSend(client, message);
        }
    }

    private static void writeAndSend(Socket clientSocket, String message) throws IOException {
        BufferedWriter outputName = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        outputName.write(message);
        outputName.newLine();
        outputName.flush();
    }

//    private static void privateMessage(Socket socket, HashMap<String, Socket> clientMap, String pmName, String user) throws IOException {
//        BufferedReader inputPM = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        String pmReceived = inputPM.readLine();
//
//        for (Map.Entry<String, Socket> set : clientMap.entrySet()) {
//            if (set.getKey().compareTo(pmName) == 0) {
//                writeAndSend(set.getValue(), Colors.PINK + user.concat(" PM: ").concat(pmReceived) + Colors.RESET);
//                return;
//            }
//        }
//        writeAndSend(socket, Colors.RED + "User doesn't exist" + Colors.RESET);
//
//    }

//    private static void writeHistory(String writeThis) {
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("serverHistory.txt", true));
//            writer.write(writeThis);
//            writer.newLine();
//            writer.close();
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//
//    }

}
