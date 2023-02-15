import java.net.Socket;

public class Player {
    Character character;
    Socket playerSocket;

    public Player(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
