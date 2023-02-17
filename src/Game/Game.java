package Game;

import gameObjects.CharacterClasses;
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

    public Game() {
        this.inCombat = false;
        this.party = new ArrayList<PlayerCharacter>();
        this.gold = 50;
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
        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/bug.txt");
        Path story = Path.of("resources/chapters/Battle1.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printBattleTwo() throws IOException {
        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));
        Path screen = Path.of("resources/ascii/game_Screens/elf.txt");
        Path story = Path.of("resources/chapters/Battle2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return null;
    }

    public String printBattleThree() {
        GameServer.setPlayerChoices(PlayerChoices.playerChoices("resources/chapters/choices/battleChoices.txt"));

        return null;
    }

    public String printFinalBattle() throws IOException {
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

    public void playerAttack() {

    }

    public void playerDefend() {

    }

    public void playerHeal() {

    }

    public void playerRun() {

    }

}
