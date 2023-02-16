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

    private String printFirstChapter() throws IOException {
        Path filePath = Path.of("resources/Chapters/Chapter1.txt");
        String content = Files.readString(filePath);
        return content;
    }

    private String printSecondChapter(){
        return null;
    }

    private String printThirdChapter(){
        return null;
    }
}
