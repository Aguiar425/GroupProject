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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private static int playerLimit;
    private static int deadPlayers = 0;
    private static HashMap<String, Socket> clientMap = new HashMap<>();
    private static HashMap<String, String> playerChoices = new HashMap<>();
    private static int playerTurn = 0;
    private static Game game;
    private static boolean occupied = false;
    private static boolean specialOne = true;
    private static boolean specialTwo = true;
    private static boolean specialThree = true;
    private static boolean specialFour = true;
    private static boolean specialFive = true;


    public GameServer() {
        game = new Game();
        ExecutorService threadFactory;
        ServerSocket serverSocket;
        int totalPlayers = 0;

        setPlayerNumber();

        try {
            serverSocket = new ServerSocket(8080);
            threadFactory = Executors.newCachedThreadPool();

            while (true) {
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

                if (totalPlayers < getPlayerLimit()) {
                    broadcastMessage(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    System.out.println(Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);

                    createPlayerThread(threadFactory, clientSocket, userName);
                } else if (totalPlayers == getPlayerLimit()) {
                    if (totalPlayers == 1) {
                        soloPlayerBuff();

                    }
                    broadcastMessage(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    broadcastMessage(Colors.BLUE + Messages.GAME_STARTED + Colors.RESET);

                    System.out.println(Colors.BLUE + Colors.BLUE + Messages.USER_JOINED.formatted(userName) + Colors.RESET);
                    System.out.println(Messages.GAME_STARTED);

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

    private static void soloPlayerBuff() {
        PlayerCharacter solo = game.getParty().get(0);
        solo.setMaxHitpoints(solo.getMaxHitpoints() + 70);
        solo.setHitpoints(solo.getMaxHitpoints());
        solo.setMinDamage(solo.getMinDamage() + 5);
        solo.setMaxDamage(solo.getMaxDamage() + 8);
        System.out.println("Solo mode");
    }

    //THESE METHODS ARE USED IN THE INITIALIZATION OF THE GAME
    private static void setPlayerNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players?");
        while (true) {
            try {
                setPlayerLimit(Integer.parseInt(scanner.nextLine()));
                System.out.println("Max player set to: " + getPlayerLimit());
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

    public static void setPlayerLimit(int playerLimit) {
        GameServer.playerLimit = playerLimit;
    }

    public static void setPlayerTurn(int playerTurn) {
        GameServer.playerTurn = playerTurn;
    }

    public static int getDeadPlayers() {
        return deadPlayers;
    }

    public static void setDeadPlayers(int deadPlayers) {
        GameServer.deadPlayers = deadPlayers;
    }

    public static void setSpecials(boolean special) {
        GameServer.specialOne = special;
        GameServer.specialTwo = special;
        GameServer.specialThree = special;
        GameServer.specialFour = special;
        GameServer.specialFive = special;
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
                try {
                    monsterThread();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }

    private void monsterThread() throws IOException, InterruptedException {
        waitFor();
        while (true) {
            System.out.println("MonsterReady");
            if (game.isInCombat()) {
                Monster monster = game.getAllMonsters();
                if (game.isFirstBossTurn()) {
                    System.out.println("firstTurnBoss check");
                    game.setFirstBossTurn(false);
                    broadcastMessage(game.printFinalBattle());
                    Thread.sleep(100);
                }
                if (monster.getHitpoints() <= 0) {
                    System.out.println("monster dead check");
                    monster.setAlive(false);
                    checkBattleCompletion(monster);

                }
                if (!monster.getAlive()) {
                    System.out.println("end battle");
                    battleEndAction(monster);

                } else {
                    continueBattle(monster);
                }
            } else {
                System.out.println("monster thread goes to sleep" + Colors.RED + "NOT A BATTLE" + Colors.RESET);
                waitFor();
            }
        }
    }

    private void continueBattle(Monster monster) {
        System.out.println("continue battle");
        //        Monster monster;
        //        monster = game.getAllMonsters();
        try {
            playerTurn = 0;
            broadcastMessage(game.monsterAttack(monster, this));
            this.notifyAll();
            System.out.println("monster thread goes to sleep");
            bossBehaviour(monster);
            waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void bossBehaviour(Monster monster) throws IOException {
        if (game.isBossBattle() && specialFive && monster.getHitpoints() <= 50) {
            specialFive = false;
            broadcastMessage("                                                GIT DRAGON: " + Colors.RED + "HAHAHAHAHAHAHHAH" + Colors.RESET);
            broadcastMessage("                                                   YOU LEAVE ME NO CHOICE");
            broadcastMessage("                                                 you think you can program...");
            broadcastMessage("                                                             but...");
            broadcastMessage("                                                             CAN");
            broadcastMessage("                                                             YOU");
            broadcastMessage(Colors.RED + "                                              GIT?????" + Colors.RESET);
            broadcastMessage(Colors.YELLOW + "                                         GIT FUCKED" + Colors.RESET);
        } else if (game.isBossBattle() && specialFour && monster.getHitpoints() <= 100) {
            specialFour = false;
            broadcastMessage("                                                       GIT DRAGON: " + Colors.RED + "YOU..." + Colors.RESET);
            broadcastMessage("                                                   the audacity you possess...");
            broadcastMessage("                                               you really think you can best " + Colors.RED + "ME?" + Colors.RESET);
        } else if (game.isBossBattle() && specialThree && monster.getHitpoints() <= 150) {
            specialThree = false;
            broadcastMessage("                                                       GIT DRAGON: " + Colors.RED + "UGHHHHHH" + Colors.RESET);
            broadcastMessage("                                                       how dare you...");
        } else if (game.isBossBattle() && specialTwo && monster.getHitpoints() <= 200) {
            specialTwo = false;
            broadcastMessage("                                                       GIT DRAGON: " + Colors.RED + "HAHAHA" + Colors.RESET);
            broadcastMessage("                                            come at me young devs, show me what you got");
        } else if (game.isBossBattle() && specialOne && monster.getHitpoints() <= 250) {
            specialOne = false;
            broadcastMessage("                                                       GIT DRAGON: HA HA HA HA");
            broadcastMessage("                                                      you guys are quite humorous");
        }

    }

    private void battleEndAction(Monster monster) {
        String gameChaptersDirectory = "resources/chapters/";
        String gameSoundsDirectory = "resources/soundFx/";
        try {
            playerTurn = 0;
            this.notifyAll();
            System.out.println("monster died");
            System.out.println(monster.getMonsterClass().getLoot().getLootDescription());
            if (monster.getMonsterClass().equals(MonsterClasses.FINAL)) {
                broadcastEpilogue(gameChaptersDirectory, gameSoundsDirectory);
                waitFor();
                return;
            }
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

    private void broadcastEpilogue(String gameChaptersDirectory, String gameSoundsDirectory) throws InterruptedException, IOException {
        game.sound.getSoundLoopVar().stop();
        game.sound.setSoundLoop(gameSoundsDirectory + "Ending-Theme.wav");
        Thread.sleep(25000); //TODO change timer to coincide with death sound
        broadcastMessage(Files.readString(Path.of(gameChaptersDirectory + "EndingChapterOne.txt")));
        Thread.sleep(5000);
        broadcastMessage(Files.readString(Path.of(gameChaptersDirectory + "EndingChapterTwo.txt")));
        Thread.sleep(5000);
        broadcastMessage(Files.readString(Path.of(gameChaptersDirectory + "EndingChapterThree.txt")));
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
        PlayerCharacter thisPlayerCharacter = null;
        for (PlayerCharacter pc : game.getParty()) {
            if (pc.getName().equals(user)) {
                thisPlayerCharacter = pc;
            }
        }
        while ((msgReceived = inputReader.readLine()) != null) {
            if (!occupied) {
                if (msgReceived.startsWith("/")) {
                    if (msgReceived.compareTo("/exit") == 0) {
                        playerLimit--;
                        break;
                    }
                    if (game.isInCombat()) {
                        synchronized (this) {
                            if (thisPlayerCharacter.getHitpoints() <= 0) {
                                writeAndSend(socket, "you are kinda dead, what do you expect you can do?");
                                playerTurn++;
                                if (playerTurn >= getPlayerLimit()) {
                                    System.out.println("wake the monster thread");
                                    this.notifyAll();
                                }
                                occupied = false;
                                this.wait();
                            } else {
                                occupied = true;
                                playerTurn(user, socket, msgReceived);
                            }
                        }
                    } else {
                        if (msgReceived.compareTo("/exit") == 0) {
                            playerLimit--;
                            break;
                        }
                        occupied = true;
                        dealWithCommand(msgReceived, socket, user);
                    }
                    Thread.sleep(100);
                    occupied = false;

                } else if (msgReceived.startsWith("@")) { //TODO stop choices from being slected in battle
                    occupied = true;
                    dealWithChoice(msgReceived);
                    Thread.sleep(1000);
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
            if (playerTurn >= getPlayerLimit()) {
                System.out.println("wake the monster thread");
                this.notifyAll();
            }
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
            writeAndSend(characterName.getValue(), message);
        }
    }

    public void writeAndSend2(Socket clientSocket, String message) throws IOException, InterruptedException {
        BufferedWriter outputName = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String[] lines = message.split("\n");
        for (String line : lines) {
            outputName.write(line);
            outputName.newLine();
            outputName.flush();
            Thread.sleep(10); // pause for 1 second between each line
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