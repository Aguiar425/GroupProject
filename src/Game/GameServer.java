package Game;

import gameObjects.CharacterClasses;
import gameObjects.Monster;
import gameObjects.PlayerCharacter;
import messages.Colors;
import messages.Messages;
import messages.commands.Commands;
import messages.commands.battleCommands.BattleCommands;

import java.io.*;
import java.lang.reflect.Method;
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
    private static HashMap<String, String> playerChoices = new HashMap<>();
    private static int playerLimit;
    private static int playerTurn = 0;
    private static Game game;
    private static boolean occupied = false;
    boolean alreadyAttacked = false;

    public GameServer() {
        game = new Game();
        ServerSocket serverSocket;
        int totalPlayers = 0;

        setPlayerNumber();

        try {
            serverSocket = new ServerSocket(8080);

            while (true) {
                ExecutorService threadFactory = Executors.newCachedThreadPool();
                final Socket clientSocket = serverSocket.accept();

                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeAndSend(clientSocket, Colors.YELLOW + Messages.ASK_FOR_USERNAME + Colors.RESET);

                String userName = consoleInput.readLine();
                clientMap.put(userName, clientSocket);

                writeAndSend(clientSocket, Colors.YELLOW + Messages.ASK_FOR_CLASS + Colors.RESET);
                writeAndSend(clientSocket, Messages.AVAILABLE_CLASSES);
                String playerClass = consoleInput.readLine().toString();
                classChoice(clientSocket, consoleInput, userName, playerClass);

                totalPlayers++;

                if (totalPlayers < playerLimit) {
                    broadcastMessage(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    System.out.println(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    createPlayerThread(threadFactory, clientSocket, userName);
                } else if (totalPlayers == playerLimit) {
                    broadcastMessage(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    broadcastMessage(Colors.BLUE + Messages.GAME_STARTED + Colors.RESET);

                    System.out.println(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    System.out.println(Messages.GAME_STARTED);

                    printMainMenu(); //TODO new game selection
                    broadcastMessage(game.startGame());
                    choicesSetup("resources/chapters/choices/chapterOneChoices.txt");
                    createPlayerThread(threadFactory, clientSocket, userName);
                    monsterThread(threadFactory);
                } else {
                    System.out.println(Messages.PLAYER_LIMIT);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setPlayerNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players?");
        while (true) {
            try {
                playerLimit = Integer.parseInt(scanner.nextLine());
                System.out.println("Max player set to: " + playerLimit);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Please insert a valid number");
            }
        }
    }

    public static void setPlayerChoices(HashMap<String, String> playerChoices) {
        GameServer.playerChoices = playerChoices;
    }

    public static int getPlayerLimit() {
        return playerLimit;
    }

    public static void setPlayerTurn(int playerTurn) {
        GameServer.playerTurn = playerTurn;
    }

    private void classChoice(Socket clientSocket, BufferedReader consoleInput, String userName, String playerClass) throws IOException {
        while (true) {
            if (playerClass.equals("R")) {
                game.createCharacter(userName, CharacterClasses.ROGUE);
                break;
            } else if (playerClass.equals("W")) {
                game.createCharacter(userName, CharacterClasses.WARRIOR);
                break;
            } else if (playerClass.equals("M")) {
                game.createCharacter(userName, CharacterClasses.MAGE);
                break;
            } else {
                writeAndSend(clientSocket, Colors.YELLOW + Messages.INVALID_INPUT + Colors.RESET);
                playerClass = consoleInput.readLine().toString();
            }
        }
    }

    private void choicesSetup(String path) {
        playerChoices = PlayerChoices.playerChoices(path);
    }

    private void createPlayerThread(ExecutorService threadFactory, Socket clientSocket, String userName) {
        Thread t = new Thread(() -> {
            try {
                playerThread(userName, clientSocket, clientMap);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.setName(userName);
        threadFactory.submit(t);
    }

    private void printMainMenu() throws IOException {
        Path filePath = Path.of("resources/ascii/Game_Screens/mainMenu.txt");
        String content = Files.readString(filePath);
        broadcastMessage(content);
        //broadcastMessage(Colors.RED + "       <N>"+ Colors.RESET+"ew Game.Game                 " + Colors.RED + "<L>"+ Colors.RESET + "oad Game.Game");
        //broadcastMessage(game.startGame());
        //BufferedReader input = new BufferedReader(new InputStreamReader(socket.GetInputStream()));
    }

    public void monsterThread(ExecutorService threadFactory) {
        threadFactory.submit(new Thread(() -> {
            synchronized (this) {
                System.out.println("monster thread goes to sleep");
                waitFor();
                while (true) {
                    if (game.isInCombat()) {
                        Monster monster = game.getAllMonsters();
                        if (monster.getHitpoints() <= 0) {
                            monster.setAlive(false);
                            game.setBattleOneComplete(true);
                        }
                        if (!monster.getAlive()) {
                            try {
                                playerTurn = 0;
                                this.notifyAll();
                                System.out.println("monster died");
                                System.out.println(monster.getMonsterClass().getLoot().getLootDescription());
                                broadcastMessage(game.printVictory(monster.getMonsterClass().getLoot().getLootDescription()));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("monster thread goes to sleep");
                            waitFor();
                        }
                        monster = game.getAllMonsters();
                        try {
                            broadcastMessage(game.monsterAttack(monster));
                            playerTurn = 0;
                            this.notifyAll();
                            System.out.println("monster thread goes to sleep");
                            waitFor();
                        } catch (InterruptedException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("monster thread goes to sleep");
                        waitFor();
                    }
                }
            }
        }));
    }

    private void playerThread(String user, Socket socket, HashMap<String, Socket> clientMap) throws IOException, InterruptedException {
        System.out.println(Colors.RED + clientMap + Colors.RESET);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msgReceived;


        while ((msgReceived = inputReader.readLine()) != null) {
            if (!occupied) {
                if (msgReceived.startsWith("/")) {
                    if (game.isInCombat()) {
                        synchronized (this) {
                            occupied = true;
                            dealWithBattle(msgReceived, user);
                            playerTurn++;
                            if (!game.isInCombat()) {
                                occupied = false;
                                playerTurn = 0;
                            } else {
                                if (playerTurn >= playerLimit) {
                                    System.out.println("wake the monster thread");
                                    this.notifyAll();
                                }
                                Thread.sleep(1000);
                                occupied = false;
                                this.wait();
                            }
                        }
                    } else {
                        occupied = true;
                        dealWithCommand(msgReceived);
                    }
                    Thread.sleep(3000);
                    occupied = false;
                } else if (msgReceived.startsWith("@")) {
                    occupied = true;
                    for (Map.Entry<String, String> set : playerChoices.entrySet()) {
                        if (set.getKey().equals(msgReceived)) {
                            stringToMethod(set.getValue(), game);

                        }
                    }
                    Thread.sleep(3000);
                    occupied = false;
                } else {
                    broadcastMessage(user + ": " + msgReceived);
                }
            } else {
                writeAndSend(socket, Messages.NO_SPAM);
            }
        }
        clientMap.remove(user, socket);
        System.out.printf(Colors.YELLOW + Messages.USER_LEFT.formatted(user) + Colors.RESET);
        broadcastMessage(Colors.YELLOW + Messages.USER_LEFT.formatted(user) + Colors.RESET);

    }

    public void broadcastMessage(String message) throws IOException {
        for (Map.Entry<String, Socket> characterName : clientMap.entrySet()) {
            writeAndSend(characterName.getValue(), message);
        }
    }

    private void writeAndSend(Socket clientSocket, String message) throws IOException {
        BufferedWriter outputName = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        outputName.write(message);
        outputName.newLine();
        outputName.flush();
    }

    private void stringToMethod(String value, Game game) {
        try {
            Class<?> clazz = Game.class;
            Method method = clazz.getDeclaredMethod(value);
            Class<?> returnType = method.getReturnType();
            broadcastMessage((String) method.invoke(game));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dealWithCommand(String message) throws IOException {
        String description = message.split(" ")[0];
        Commands command = Commands.getCommand(description);

        if (command == null) {
            broadcastMessage("NO SUCH COMMAND EXISTS");
            return;
        }
        command.getHandler().execute(GameServer.this, this.game);
    }

    private void dealWithBattle(String message, String name) throws IOException {
        String description = message.split(" ")[0];
        BattleCommands command = BattleCommands.getCommand(description);

        if (command == null) {
            //TODO change to allow multiple tries
            broadcastMessage("NO SUCH COMMAND EXISTS");
            return;
        }

        for (PlayerCharacter pc : game.getParty()) {
            if (pc.getName().equals(name)) {

                command.getBattleHandler().execute(GameServer.this, this.game, pc);
                return;
            }
        }
    }

    private void waitFor() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startCombat() {

    }

    public HashMap<String, Socket> getClientMap() {
        return clientMap;
    }
}