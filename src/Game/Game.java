package Game;

import gameObjects.CharacterClasses;
import gameObjects.Monster;
import gameObjects.MonsterClasses;
import gameObjects.PlayerCharacter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int currentRoom;
    private boolean inCombat;
    private List party;
    private int gold;

    private Monster[] allMonsters;

    private Boolean battleOneComplete;
    private Boolean battleTwoComplete;
    private Boolean battleThreeComplete;
    private Boolean battleFinalComplete;

    public Game() {
        this.inCombat = false;
        this.party = new ArrayList<PlayerCharacter>();
        this.gold = 50;
        this.battleOneComplete = false;
        this.battleTwoComplete = false;
        this.battleThreeComplete = false;
        this.battleFinalComplete = false;

    }

    public String startGame() throws IOException {
        return printChapterOne();
    }

    public String printChapterZero() throws IOException {
        Path filePath = Path.of("resources/chapters/Chapter0.txt");
        String content = Files.readString(filePath);
        return content;
    }

    public String printChapterOne() throws IOException {
        setCurrentRoom(1);
        System.out.println("Party is on room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterOneChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/hallway_LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter1.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterTwo() throws IOException {
        setCurrentRoom(2);
        System.out.println("Party is on room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterTwoChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterThree() throws IOException {
        setCurrentRoom(3);
        System.out.println("Party is on room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterThreeChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter3.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterFour() throws IOException {
        setCurrentRoom(4);
        System.out.println("Party is on room: " + getCurrentRoom());

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterFourChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/threeDoors.txt");
        Path story = Path.of("resources/chapters/Chapter4.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printBattleOne() throws IOException {
        if(battleOneComplete){
            GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/chapterOneChoices.txt"));
            Path screen = Path.of("resources/ascii/skull.txt");
        }
        allMonsters[0] = new Monster(MonsterClasses.BUG);
        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/bug.txt");
        Path story = Path.of("resources/chapters/Battle1.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printBattleTwo() throws IOException {
        allMonsters[1] = new Monster(MonsterClasses.ELF);

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/elf.txt");
        Path story = Path.of("resources/chapters/Battle2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return null;
    }

    public String printBattleThree() throws IOException {
        allMonsters[2] = new Monster(MonsterClasses.GRIFFIN);

        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/griffin.txt");
        Path story = Path.of("resources/chapters/Battle3.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printFinalBattle() throws IOException {
        allMonsters[3] = new Monster(MonsterClasses.FINAL);
        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/dragon.txt");
        Path story = Path.of("resources/chapters/Chapter4.txt");
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

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List getParty() {
        return party;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void createCharacter(String name, CharacterClasses classChoice) {
        PlayerCharacter pc = new PlayerCharacter(classChoice, name);
        party.add(pc);
    }

    public void something() {
        System.out.println("something");
    }

    public void playerAttack(int target, int damage) {
        allMonsters[target].setHitpoints(allMonsters[target].getHitpoints() - damage);
    }

    public void playerDefend() {

    }

    public void playerHeal() {

    }

    public void playerRun() {

    }

}
