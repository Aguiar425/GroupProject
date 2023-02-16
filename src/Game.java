import java.io.File;
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
       return printFirstChapter();
    }

    public String printFirstChapter() throws IOException {
        Path filePath = Path.of("resources/Chapters/Chapter1.txt");
        String content = Files.readString(filePath);
        return content;
    }

    public String printSecondChapter() throws IOException {
        Path screen = Path.of("resources/ascii/Game_Screens/hallway_LeftRight.txt");
        Path story = Path.of("resources/Chapters/Chapter2.txt");
        String content = Files.readString(screen) + "\n" + Files.readString(story);
        return content;
    }

    public String printThirdChapter(){
        return null;
    }
}
