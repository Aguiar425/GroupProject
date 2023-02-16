package messages.commands;

import Game.Game;
import Game.GameServer;
import messages.Colors;

import java.io.IOException;

public class BackCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game) {
        try {
            switch (game.getCurrentRoom()) {
                case 1:
                    server.broadcastMessage(Colors.RED + "You can't go back, there is no escape".toUpperCase() + Colors.RESET);
                case 2:
                    server.broadcastMessage(game.printChapterOne());
                case 3:
                    server.broadcastMessage(game.printChapterOne());
                case 4:
                    server.broadcastMessage(game.printChapterThree());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
