package Game;

import gameObjects.*;
import messages.Colors;
import messages.Messages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private static int currentRoom;
    private static boolean inCombat;
    private static Boolean battleOneComplete;
    private static Boolean battleTwoComplete;
    private static Boolean battleThreeComplete;
    private final String gameScreensDirectory = "resources/ascii/game_Screens/";
    private final String gameChaptersDirectory = "resources/chapters/";
    private final String gameChoicesDirectory = "resources/chapters/choices/";
    ExecutorService threadFactory;
    private List party;
    private int gold;
    private int healingPotions;
    private Monster[] allMonsters;
    //private Boolean battleFinalComplete;
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
        //this.battleFinalComplete = false;
        this.shopHasKey = true;
        this.shopHasPotionOne = true;
        this.shopHasPotionTwo = true;

    }

    public static void setBattleOneComplete(Boolean battleOneComplete) {
        Game.battleOneComplete = battleOneComplete;
    }

    public static void setBattleTwoComplete(Boolean battleTwoComplete) {
        Game.battleTwoComplete = battleTwoComplete;
    }

    public static void setBattleThreeComplete(Boolean battleThreeComplete) {
        Game.battleThreeComplete = battleThreeComplete;
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
        Path filePath = Path.of(gameChaptersDirectory + "Chapter0.txt");
        String content = Files.readString(filePath);
        return content;
    }

    public String printChapterOne() throws IOException {
        setCurrentRoom(1);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterOneChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "hallway_LeftRight.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter1.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterTwo() throws IOException {
        setCurrentRoom(2);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterTwoChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "LeftRight.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterThree() throws IOException {
        setCurrentRoom(3);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterThreeChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "LeftRight.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter3.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterFour() throws IOException {
        setCurrentRoom(4);
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterFourChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "threeDoors.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter4.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printBattleOne() throws IOException {
        setCurrentRoom(21);
        if (this.battleOneComplete) {
            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterOneChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "skull.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle1_Complete.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        } else {
            inCombat = true;

            Path screen = Path.of(gameScreensDirectory + "bug.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle1.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);

            return content;
        }
    }

    public String printBattleTwo() throws IOException {
        if (battleTwoComplete) {
            setCurrentRoom(22);
            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterTwoChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "skull.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle2_Complete.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        } else {
            inCombat = true;
            setCurrentRoom(22);
            battleTwoComplete = true;

            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "battleChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "elf.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle2.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        }
    }

    public String printBattleThree() throws IOException {
        if (battleThreeComplete) {
            setCurrentRoom(23);
            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterThreeChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "skull.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle3_Complete.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        } else {
            inCombat = true;
            setCurrentRoom(23);
            battleThreeComplete = true;

            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "battleChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "griffin.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle3.txt");
            String content = Files.readString(screen) + "\n" + Files.readString(story);
            return content;
        }
    }

    public String printFinalBattle() throws IOException {
        inCombat = true;
        setCurrentRoom(24);

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "battleChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "dragon.txt");
        Path story = Path.of(gameChaptersDirectory + "BattleFinal.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printShop() throws IOException {
        setCurrentRoom(10);
        System.out.println("Party is on room(shop): " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "shopChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "shop.txt");
        Path story = Path.of(gameChaptersDirectory + "Shop.txt");
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

    public String printVictory(String lootMessage) throws IOException {
        inCombat = false;
        Path screen = Path.of(gameScreensDirectory + "victory.txt");
        String content = Files.readString(screen) + "\n The party obtained " + lootMessage +
                "\nGo back to continue your adventure.";
        return content;
    }

    public String monsterAttack(Monster monster) throws InterruptedException {
        int targetIndex = RandomNumber.randomizer(0, GameServer.getPlayerLimit());
        PlayerCharacter target = (PlayerCharacter) party.get(targetIndex);
        int damage = RandomNumber.randomizer(monster.getMinDamage(), monster.getMaxDamage());
        if (target.isDefending()) {
            damage = (int) (damage / 2);
            target.setDefending(false);
        }
        target.setHitpoints(target.getHitpoints() - damage);
        System.out.println(target.getName().concat(" received ") + Colors.RED + damage + Colors.RESET + " of damage!");
        return target.getName().concat(" received ") + Colors.RED + damage + Colors.RESET + " of damage!";
    }

    public int getHealingPotions() {
        return healingPotions;
    }

    public void setHealingPotions(int healingPotions) {
        this.healingPotions = healingPotions;
    }
}

