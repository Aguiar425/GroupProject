package Game;

import gameObjects.CharacterClasses;
import gameObjects.Monster;
import gameObjects.MonsterClasses;
import gameObjects.PlayerCharacter;
import messages.Colors;
import messages.Messages;
import messages.commands.Commands;
import messages.commands.battleCommands.BattleCommands;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
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
                String playerClass = consoleInput.readLine();
                classChoice(clientSocket, consoleInput, userName, playerClass);

                totalPlayers++;
                if (totalPlayers < playerLimit) {
                    broadcastMessage(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET + "\n");

                    System.out.println(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    createPlayerThread(threadFactory, clientSocket, userName);
                } else if (totalPlayers == playerLimit) {

                    broadcastMessage(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET + "\n");
                    broadcastMessage(Colors.BLUE + Messages.GAME_STARTED + Colors.RESET + "\n");

                    System.out.println(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    System.out.println(Messages.GAME_STARTED);
                    Thread.sleep(500);
                    game.printMainMenu(this); //TODO new game selection SERIALIZABLE
                    Thread.sleep(5000);
                    broadcastMessage(game.startGame());
                    //choicesSetup("resources/chapters/choices/chapterZeroChoices.txt");
                    createPlayerThread(threadFactory, clientSocket, userName);
                    createMonsterThread(threadFactory);

                } else {
                    System.out.println(Messages.PLAYER_LIMIT);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //THESE METHODS ARE USED IN THE INITIALIZATION OF THE GAME
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

    private static void checkBattleCompletion(Monster monster) {
        if (monster.getMonsterClass().equals(MonsterClasses.BUG)) {
            Game.setBattleOneComplete(true);

        } else if (monster.getMonsterClass().equals(MonsterClasses.ELF)) {
            Game.setBattleTwoComplete(true);

        } else if (monster.getMonsterClass().equals(MonsterClasses.GRIFFIN)) {
            Game.setBattleThreeComplete(true);
        }
    }

    private static void giveLoot(Monster monster) {
        game.setGold(game.getGold() + monster.getMonsterClass().getLoot().getLootGold());
        game.setHealingPotions(game.getHealingPotions() + monster.getMonsterClass().getLoot().getLootPotions());
        game.setKeyCounter(game.getKeyCounter() + monster.getMonsterClass().getLoot().getLootKeys());
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
                playerClass = consoleInput.readLine();
            }
        }
    }

    private void choicesSetup(String path) {
        playerChoices = PlayerChoices.playerChoices(path);
    }

    //THESE METHODS ARE RELATED TO THE MONSTER THREAD
    public void createMonsterThread(ExecutorService threadFactory) {
        threadFactory.submit(new Thread(() -> {
            synchronized (this) {
                System.out.println("monster thread goes to sleep");
                monsterThread();
            }
        }));
    }

    private void monsterThread() {
        waitFor();
        while (true) {
            if (game.isInCombat()) {
                Monster monster = game.getAllMonsters();
                if (monster.getHitpoints() <= 0) {
                    monster.setAlive(false);
                    checkBattleCompletion(monster);
                }
                if (!monster.getAlive()) {
                    battleEndAction(monster);
                } else {
                    continueBattle();
                }
            } else {
                System.out.println("monster thread goes to sleep" + Colors.RED + "NOT A BATTLE" + Colors.RESET);
                waitFor();
            }
        }
    }

    private void continueBattle() {
        Monster monster;
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
    }

    private void battleEndAction(Monster monster) {
        try {
            playerTurn = 0;
            this.notifyAll();
            System.out.println("monster died");
            System.out.println(monster.getMonsterClass().getLoot().getLootDescription());
            giveLoot(monster);
            broadcastMessage(game.printVictory(monster.getMonsterClass().getLoot().getLootDescription()));
            if (monster.getMonsterClass().equals(MonsterClasses.GRIFFIN)) {
                Thread.sleep(2000);
                broadcastMessage(game.printChestTwo());
            }
            System.out.println("monster thread goes to sleep" + Colors.RED + "DEAD" + Colors.RESET);
            waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // THESE METHODS ARE RELATED TO THE PLAYER THREAD
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
                            playerTurn(user, socket, msgReceived);
                        }
                    } else {
                        occupied = true;
                        dealWithCommand(msgReceived, socket, user);
                    }
                    Thread.sleep(3000);
                    occupied = false;
                } else if (msgReceived.startsWith("@")) {
                    occupied = true;
                    dealWithChoice(msgReceived);
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

    private void playerTurn(String user, Socket socket, String msgReceived) throws IOException, InterruptedException {
        dealWithBattle(msgReceived, user, socket);
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

    private void dealWithChoice(String msgReceived) {
        for (Map.Entry<String, String> set : playerChoices.entrySet()) {
            if (set.getKey().equals(msgReceived)) {
                stringToMethod(set.getValue(), game);
            }
        }
    }

    private void dealWithCommand(String message, Socket socket, String name) throws IOException {
        String description = message.split(" ")[0];
        Commands command = Commands.getCommand(description);

        if (command == null) {
            writeAndSend(socket, Messages.NO_BATTLE_COMMAND);
            return;
        }
        command.getHandler().execute(GameServer.this, game, socket, name);
    }

    private void dealWithBattle(String message, String name, Socket socket) throws IOException {
        String description = message.split(" ")[0];
        BattleCommands command = BattleCommands.getCommand(description);

        while (command == null) {
            writeAndSend(socket, "DUE TO YOUR BAD TYPING SKILLS, YOU HAVE LOST YOUR TURN");
            writeAndSend(socket, "Just kidding! try again.");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            description = input.readLine();
            description = description.split(" ")[0];
            command = BattleCommands.getCommand(description);
            System.out.println("input = " + input);
            System.out.println("command = " + command);
        }

        for (PlayerCharacter pc : game.getParty()) {
            if (pc.getName().equals(name)) {
                command.getBattleHandler().execute(GameServer.this, game, pc);
                return;
            }
        }
    }

    //THESE ARE METHODS THAT ARE USED EVERYWHERE
    public void broadcastMessage(String message) throws IOException {
        for (Map.Entry<String, Socket> characterName : clientMap.entrySet()) {
            try {
                broadcastLineByLine(characterName.getValue(), message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void broadcastLineByLine(Socket clientSocket, String message) throws IOException, InterruptedException {
        BufferedWriter outputName = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String[] lines = message.split("\n");
        //outputName.newLine();
        for (String line : lines) {
            outputName.write(line);
            //outputName.newLine();
            outputName.flush();
            Thread.sleep(25); // pause for 1 second between each line
        }
    }

    public void writeAndSend(Socket clientSocket, String message) throws IOException {
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

    private void waitFor() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}