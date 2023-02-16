import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Game {
    private boolean inCombat;
    private int currentRoom;

    public Game() {
        this.inCombat = false;
        this.currentRoom = 0;
    }

    public String startGame() throws IOException {
       return printChapterZero();
    }

    public String printChapterZero() throws IOException {
        Path filePath = Path.of("resources/chapters/Chapter0.txt");
        String content = Files.readString(filePath);
        return content;
    }

    public String printChapterOne() throws IOException {
        Path screen = Path.of("resources/ascii/game_Screens/hallway_LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter1.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterTwo() throws IOException{
        Path screen = Path.of("resources/ascii/game_Screens/LeftRight.txt");
        Path story = Path.of("resources/chapters/Chapter2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printChapterThree(){
        return null;
    }

    public String printChapterFour(){
        return null;
    }

    public String printBattleOne(){
        return null;
    }

    public String printBattleTwo(){
        return null;
    }

    public String printBattleThree(){
        return null;
    }

    public String printFinalBattle(){
        return null;
    }

    public String printShop(){
        return null;
    }

    public String printChest(){
        return null;
    }
}
