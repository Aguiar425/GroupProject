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
    private static int gold;
    private static int healingPotions;
    //private Boolean battleFinalComplete;
    private static Boolean shopHasKey;
    private static int keyCounter;
    private static Boolean chestOneOpened;
    private static Boolean chestTwoOpened;
    private final String gameScreensDirectory = "resources/ascii/game_Screens/";
    private final String gameChaptersDirectory = "resources/chapters/";
    private final String gameChoicesDirectory = "resources/chapters/choices/";
    private final String gameSoundsDirectory = "resources/soundFx/";
    private final Monster[] allMonsters;
    //private final Clip dungeonLoop;
    ExecutorService threadFactory;
    Sound sound;
    private List<PlayerCharacter> party;
    private static Boolean partyHasRogue;

    public Game() {
        this.threadFactory = Executors.newCachedThreadPool();
        this.sound = new Sound();
        this.inCombat = false;
        this.party = new ArrayList<PlayerCharacter>();
        this.partyHasRogue = false;
        this.allMonsters = new Monster[4];
        this.gold = 50;
        this.battleOneComplete = false;
        this.battleTwoComplete = false;
        this.battleThreeComplete = false;
        this.healingPotions = 0;
        //this.battleFinalComplete = false;
        this.shopHasKey = true;
        this.keyCounter = 0;
        this.chestOneOpened = false;
        this.chestTwoOpened = false;
        this.sound.setDungeonSoundLoop(gameSoundsDirectory + "Dungeon-Theme.wav");
        this.sound.getDungeonSoundLoopVar().stop();
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

    public void createCharacter(String name, CharacterClasses classChoice) {
        PlayerCharacter pc = new PlayerCharacter(classChoice, name);
        party.add(pc);
    }

    public void populateMonsters() {
        allMonsters[0] = new Monster(MonsterClasses.BUG);
        allMonsters[1] = new Monster(MonsterClasses.ELF);
        allMonsters[2] = new Monster(MonsterClasses.GRIFFIN);
        allMonsters[3] = new Monster(MonsterClasses.FINAL);
    }

    public String startGame() throws IOException {

        for (PlayerCharacter pc: party) {
            if(pc.getCharacterClass().equals(CharacterClasses.ROGUE)){
                setPartyHasRogue(true);
            }
        }
        populateMonsters(); //TODO CHANGE TO CHAPTER 0
        return printChapterOne();
    }

    //THERE ARE THE METHODS FOR PRINTING THE DIFFERENT ROOMS
    public String printChapterZero() throws IOException { //TODO IMPLEMENT CHAPTER ZERO AND ADD DELAY BETWEEN SCREENS. MAYBE WAIT FOR ANY INPUT?
        Path filePath = Path.of(gameChaptersDirectory + "Chapter0.txt");
        return Files.readString(filePath);
    }

    public String printChapterOne() throws IOException {
        setCurrentRoom(1);
        if (!Sound.getDungeonSoundLoopVar().isRunning()) {
            this.sound.getDungeonSoundLoopVar().start();
        }
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterOneChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "hallway_LeftRight.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter1.txt");
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    public String printChapterTwo() throws IOException {
        setCurrentRoom(2);
        if (!Sound.getDungeonSoundLoopVar().isRunning()) {
            this.sound.getDungeonSoundLoopVar().start();
        }
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterTwoChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "LeftRight.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter2.txt");
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    public String printChapterThree() throws IOException {
        setCurrentRoom(3);
        if (!sound.getDungeonSoundLoopVar().isRunning()) {
            this.sound.getDungeonSoundLoopVar().start();
        }
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterThreeChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "LeftRight.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter3.txt");
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    public String printChapterFour() throws IOException {
        if(shopHasKey && !partyHasRogue){
            setCurrentRoom(51);
            Path story = Path.of(gameChaptersDirectory + "Chapter3_DoorLocked.txt");
            return Files.readString(story);
        } else if (shopHasKey && partyHasRogue) {
            return Messages.LOCKED_DOOR_ROGUE+"\n" + accessChapterFour();
        } else {
            return accessChapterFour();
        }
    }

    private String accessChapterFour() throws IOException {
        setCurrentRoom(4);
        if (!sound.getDungeonSoundLoopVar().isRunning()) {
            this.sound.getDungeonSoundLoopVar().start();
        }
        System.out.println("Party is in room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterFourChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "threeDoors.txt");
        Path story = Path.of(gameChaptersDirectory + "Chapter4.txt");
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    public String printChapterFinal() throws IOException {
        setCurrentRoom(5);

        sound.getDungeonSoundLoopVar().stop();
        sound.setSoundLoop(gameSoundsDirectory + "FinalRoom.wav");
        System.out.println("Party is in room : Before Final Boss");

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "chapterFourChoices.txt"));
        Path story = Path.of(gameChaptersDirectory + "Chapter5.txt");
        return Files.readString(story);
    }

    //THESE ARE THE METHODS TO PRINT THE BATTLE ROOMS
    public String printBattleOne() throws IOException, InterruptedException {
        setCurrentRoom(21);
        if (battleOneComplete) {
            Path screen = Path.of(gameScreensDirectory + "skull.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle1_Complete.txt");
            return Files.readString(screen) + "\n" + Files.readString(story);
        } else {
            Sound.getDungeonSoundLoopVar().stop();
            sound.setSoundClip(gameSoundsDirectory + "BattleStart-Theme.wav");
            Thread.sleep(1900);
            sound.setSoundLoop(gameSoundsDirectory + "Battle-Theme.wav");
            inCombat = true;

            Path screen = Path.of(gameScreensDirectory + "bug.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle1.txt");
            return Files.readString(screen) + "\n" + Files.readString(story);
        }
    }

    public String printBattleTwo() throws IOException, InterruptedException {
        setCurrentRoom(22);
        if (battleTwoComplete) {
            Path screen = Path.of(gameScreensDirectory + "skull.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle2_Complete.txt");
            return Files.readString(screen) + "\n" + Files.readString(story);
        } else {
            Sound.getDungeonSoundLoopVar().stop();
            sound.setSoundClip(gameSoundsDirectory + "BattleStart-Theme.wav");
            Thread.sleep(1900);
            sound.setSoundLoop(gameSoundsDirectory + "Battle-Theme.wav");
            inCombat = true;

            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "battleChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "elf.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle2.txt");
            return Files.readString(screen) + "\n" + Files.readString(story);
        }
    }

    public String printBattleThree() throws IOException, InterruptedException {
        setCurrentRoom(23);
        if (battleThreeComplete) {
            this.sound.getDungeonSoundLoopVar().start();
            Path screen = Path.of(gameScreensDirectory + "skull.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle3_Complete.txt");
            return Files.readString(screen) + "\n" + Files.readString(story);
        } else {
            this.sound.getDungeonSoundLoopVar().stop();
            sound.setSoundClip(gameSoundsDirectory + "BattleStart-Theme.wav");
            Thread.sleep(1900);
            sound.setSoundLoop(gameSoundsDirectory + "Battle-Theme.wav");
            inCombat = true;

            GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "battleChoices.txt"));
            Path screen = Path.of(gameScreensDirectory + "griffin.txt");
            Path story = Path.of(gameChaptersDirectory + "Battle3.txt");
            return Files.readString(screen) + "\n" + Files.readString(story);
        }
    }

    public String printFinalBattle() throws IOException {
        setCurrentRoom(24); //TODO KEY VALIDATION AND FINAL BOSS MUSIC
        sound.getSoundLoopVar().stop();
        sound.setSoundLoop(gameSoundsDirectory + "FinalBoss-Theme.wav");
        inCombat = true;

        Path screen = Path.of(gameScreensDirectory + "dragon.txt");
        Path story = Path.of(gameChaptersDirectory + "BattleFinal.txt");
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    //THESE ARE THE METHODS TO PRINT THE SHOP AND CHEST ROOMS
    public String printShop() throws IOException {
        setCurrentRoom(10);
        this.sound.getDungeonSoundLoopVar().stop();
        sound.setSoundLoop(gameSoundsDirectory + "Shop-Theme.wav");
        System.out.println("Party is on room(shop): " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "shopChoices.txt"));
        Path screen = Path.of(gameScreensDirectory + "shop.txt");
        Path story = Path.of(gameChaptersDirectory + "Shop.txt");
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    public String buyKey() {
        if (shopHasKey) {
            if (gold < 75) {
                System.out.println(Messages.NOT_ENOUGH_GOLD);
                return Messages.NOT_ENOUGH_GOLD;
            } else {
                gold -= 75;
                shopHasKey = false;
                keyCounter++;
                return Messages.KEY_BOUGHT;
            }

        } else {
            return Messages.KEY_ALREADY_BOUGHT;
        }
    }

    public String buyPotion() {
        if (gold < 25) {
            return Messages.NOT_ENOUGH_GOLD;
        } else {
            gold -= 25;
            healingPotions++;
            return Messages.POTION_BOUGHT;
        }
    }

    public String printChestOne() throws IOException { //TODO PRINT EMPTY CHEST ART
        setCurrentRoom(31);
        if (chestOneOpened) {

            return Messages.CHEST_ALREADY_OPENED;
        }
        chestOneOpened = true;
        System.out.println("party is on chest room" + getCurrentRoom());
        this.sound.getDungeonSoundLoopVar().stop();
        sound.setSoundClip(gameSoundsDirectory + "effects/Chest-Theme.wav");
        Path screen = Path.of(gameScreensDirectory + "chest.txt");
        Path story = Path.of(gameChaptersDirectory + "ChestOne.txt");
        gold += 150;
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    public String printChestTwo() throws IOException {
        setCurrentRoom(32);
        if (chestTwoOpened) {
            return Messages.CHEST_ALREADY_OPENED;
        }
        chestTwoOpened = true;
        this.sound.getSoundLoopVar().stop();
        System.out.println("party is on chest room" + getCurrentRoom());

        sound.setSoundClip(gameSoundsDirectory + "effects/Chest-Theme.wav");
        Path screen = Path.of(gameScreensDirectory + "chest.txt");
        Path story = Path.of(gameChaptersDirectory + "ChestTwo.txt");

        for (PlayerCharacter pc :
                party) {
            pc.setMaxHitpoints(pc.getMaxHitpoints() + 20);
            pc.setMinDamage(pc.getMinDamage() + 5);
            pc.setMaxDamage(pc.getMaxDamage() + 2);
        }
        return Files.readString(screen) + "\n" + Files.readString(story);
    }

    //THESE ARE GENERAL METHODS
    public String printGoodEnding(String lootMessage) throws IOException {
        inCombat = false; //TODO DON'T FORGET THE GOOD ENDING
        Path screen = Path.of(gameScreensDirectory + "victory.txt");
        return Files.readString(screen) + "\n The party obtained " + lootMessage +
                "\nGo back to continue your adventure.";
    }

    public String printBadEnding() throws IOException {
        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "badEndingChoicesOne.txt"));
        setCurrentRoom(41);

        Path screen = Path.of(gameScreensDirectory + "badEnding.txt");
        Path story = Path.of(gameChaptersDirectory + "BadEndingChapterOne.txt");
        return Files.readString(screen) + "\n" + Files.readString(story); //TODO DON'T FORGET THE BAD ENDING
    }

    public String printBadChoicesTwo() throws IOException {
        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "badEndingChoicesTwo.txt"));
        return Files.readString(Path.of(gameChaptersDirectory + "BadEndingChapterTwo.txt"));
    }

    public String printBadChoicesThree() throws IOException {
        GameServer.setPlayerChoices(PlayerChoices.playerChoices(gameChoicesDirectory + "badEndingChoicesThree.txt"));
        return Files.readString(Path.of(gameChaptersDirectory + "BadEndingChapterThree.txt"));
    }

    public String printVictory(String lootMessage) throws IOException {
        sound.getSoundLoopVar().stop();
        sound.setSoundLoop(gameSoundsDirectory + "Victory-Theme.wav");
        inCombat = false;
        Path screen = Path.of(gameScreensDirectory + "victory.txt");
        return Files.readString(screen) + "\n The party obtained " + lootMessage +
                "\nGo back to continue your adventure.";
    }


    public String printDefeat() throws IOException {
        //TODO DON'T FORGET THE GAME OVER (DARKSOULS)
        Path screen = Path.of(gameScreensDirectory + "gameOver.txt");
        return Files.readString(screen);
    }

    public String monsterAttack(Monster monster) throws InterruptedException {
        int targetIndex = RandomNumber.randomizer(0, GameServer.getPlayerLimit());
        PlayerCharacter target = party.get(targetIndex);
        int damage = RandomNumber.randomizer(monster.getMinDamage(), monster.getMaxDamage());
        if (target.isDefending()) {
            damage = (int) (damage / 2);
            target.setDefending(false);
        }
        target.setHitpoints(target.getHitpoints() - damage);
        System.out.println(target.getName().concat(" received ") + Colors.RED + damage + Colors.RESET + " of damage!");
        return target.getName().concat(" received ") + Colors.RED + damage + Colors.RESET + " of damage!";
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

    //THESE ARE GETTER AND SETTERS
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

    public int getHealingPotions() {
        return healingPotions;
    }

    public void setHealingPotions(int healingPotions) {
        this.healingPotions = healingPotions;
    }

    public int getKeyCounter() {
        return keyCounter;
    }

    public void setKeyCounter(int keyCounter) {
        this.keyCounter = keyCounter;
    }

    public static Boolean getPartyHasRogue() {
        return partyHasRogue;
    }

    public static void setPartyHasRogue(Boolean partyHasRogue) {
        Game.partyHasRogue = partyHasRogue;
    }

    public void startAndStopLoops() {
        this.sound.getSoundLoopVar().stop();
        this.sound.getDungeonSoundLoopVar().stop();
    }
}

