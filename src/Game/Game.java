package Game;

import gameObjects.CharacterClasses;
import gameObjects.Monster;
import gameObjects.MonsterClasses;
import gameObjects.PlayerCharacter;
import messages.Colors;
import messages.Messages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private static int currentRoom;
    private static boolean inCombat;
    ExecutorService threadFactory;
    private List party;
    private Set partySet;
    private int gold;
    private Monster[] allMonsters;
    private Boolean battleOneComplete;
    private Boolean battleTwoComplete;
    private Boolean battleThreeComplete;
    private Boolean battleFinalComplete;
    private Boolean shopHasKey;
    private Boolean shopHasPotionOne;
    private Boolean shopHasPotionTwo;

    public Game() {
        this.threadFactory = Executors.newCachedThreadPool();
        this.inCombat = false;
        this.party = new ArrayList<PlayerCharacter>();
        this.allMonsters = new Monster[4];
        this.gold = 50;
        this.battleOneComplete = false;
        this.battleTwoComplete = false;
        this.battleThreeComplete = false;
        this.battleFinalComplete = false;
        this.shopHasKey = true;
        this.shopHasPotionOne = true;
        this.shopHasPotionTwo = true;

    }

    public String startGame() throws IOException {
        populateMonsters();
        return printChapterOne();
    }

    public void populateMonsters() {
        allMonsters[0] = new Monster(MonsterClasses.BUG);
        allMonsters[1] = new Monster(MonsterClasses.ELF);
        allMonsters[2] = new Monster(MonsterClasses.GRIFFIN);
        allMonsters[3] = new Monster(MonsterClasses.FINAL);

    }

    public String printChapterZero() throws IOException {
        Path filePath = Path.of("resources/chapters/Chapter0.txt");
        String content = Files.readString(filePath);
        return content;
    }

    public String printChapterOne() throws IOException {
        setCurrentRoom(1);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterOneChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/hallway_LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter1.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterTwo() throws IOException {
        setCurrentRoom(2);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterTwoChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterThree() throws IOException {
        setCurrentRoom(3);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterThreeChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter3.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterFour() throws IOException {
        setCurrentRoom(4);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterFourChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/threeDoors.txt");
        Path story = Path.of("resources/chapters/Chapter4.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printBattleOne() throws IOException {
        if (battleOneComplete) {
            setCurrentRoom(21);
            GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterOneChoices.txt"));
            Path screen = Path.of("resources/ascii/skull.txt");
            Path story = Path.of("resources/chapters/Battle1_Complete.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        } else {
            inCombat = true;
            setCurrentRoom(21);

            Path screen = Path.of("resources/ascii/game_Screens/bug.txt");
            Path story = Path.of("resources/chapters/Battle1.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);

            return content;
        }
    }

    public String printBattleTwo() throws IOException {
        if (battleTwoComplete) {
            setCurrentRoom(22);
            GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterTwoChoices.txt"));
            Path screen = Path.of("resources/ascii/skull.txt");
            Path story = Path.of("resources/chapters/Battle2_Complete.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        } else {
            inCombat = true;
            setCurrentRoom(22);

            GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
            Path screen = Path.of("resources/ascii/game_Screens/elf.txt");
            Path story = Path.of("resources/chapters/Battle2.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        }
    }

    public String printBattleThree() throws IOException {
        if (battleThreeComplete) {
            setCurrentRoom(23);
            GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterThreeChoices.txt"));
            Path screen = Path.of("resources/ascii/skull.txt");
            Path story = Path.of("resources/chapters/Battle3_Complete.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        } else {
            inCombat = true;
            setCurrentRoom(23);

            GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
            Path screen = Path.of("resources/ascii/game_Screens/griffin.txt");
            Path story = Path.of("resources/chapters/Battle3.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        }
    }

    public String printFinalBattle() throws IOException {
        inCombat = true;
        setCurrentRoom(24);

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/dragon.txt");
        Path story = Path.of("resources/chapters/BattleFinal.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printShop() throws IOException {
        setCurrentRoom(10);
        System.out.println("Party is on room(shop): " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/shopChoices.txt"));
        Path screen = Path.of("resources/ascii/shop.txt");
        Path story = Path.of("resources/chapters/Shop.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChest() {
        return null;
    }

    public String printBadEnding() {
        return null;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<PlayerCharacter> getParty() {
        return party;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    //TODO broadcast the message to the players - return String?
    public void buyKey() {
        if (gold < 75) {
            System.out.println(Messages.NOT_ENOUGH_GOLD);
        } else {
            shopHasKey = false;
        }
    }

    public void createCharacter(String name, CharacterClasses classChoice) {
        PlayerCharacter pc = new PlayerCharacter(classChoice, name);
        party.add(pc);
    }

    public Monster getAllMonsters() {
        if (currentRoom == 21) {
            return allMonsters[0];
        } else if (currentRoom == 22) {
            return allMonsters[1];
        } else if (currentRoom == 23) {
            return allMonsters[2];
        } else {
            return allMonsters[3];
        }
    }

    public String printVictory() {
        inCombat = false;


        return null;
    }

    public String monsterAttack(Monster monster) throws InterruptedException {
        Thread.sleep(1000);
        PlayerCharacter target = (PlayerCharacter) party.get(0);
        target.setHitpoints(target.getHitpoints() - monster.getMaxDamage());
        System.out.println(target.getName().concat(" received ") + Colors.RED + monster.getMaxDamage() + Colors.RESET + " of damage!");

        return target.getName().concat(" received ") + Colors.RED + monster.getMaxDamage() + Colors.RESET + " of damage!";
    }
}
