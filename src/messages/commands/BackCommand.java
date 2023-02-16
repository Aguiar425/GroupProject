package messages.commands;

import Game.Game;
import Game.GameServer;
import messages.Colors;

import java.io.IOException;

public class BackCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game) {
        try {
//            switch (game.getCurrentRoom()) {
//                case 1:
//                    server.broadcastMessage(Colors.RED + "You can't go back, there is no escape".toUpperCase() + Colors.RESET);
//                case 2:
//                    server.broadcastMessage(game.printChapterOne());
//                case 3:
//                    server.broadcastMessage(game.printChapterOne());
//                case 4:
//                    server.broadcastMessage(game.printChapterThree());
//            }
            if (game.getCurrentRoom() == 2 || game.getCurrentRoom() == 3) {
                System.out.println(game.getCurrentRoom());
                server.broadcastMessage(game.printChapterOne());
                game.setCurrentRoom(1);
            } else if (game.getCurrentRoom() == 4) {
                System.out.println(game.getCurrentRoom());
                server.broadcastMessage(game.printChapterThree());
                game.setCurrentRoom(4);
            } else if (game.getCurrentRoom() == 10) {
                System.out.println(game.getCurrentRoom());
                server.broadcastMessage(game.printChapterTwo());
                game.setCurrentRoom(2);
            } else {
                System.out.println(game.getCurrentRoom());
                server.broadcastMessage(Colors.RED + "You can't go back, there is no escape".toUpperCase() + Colors.RESET);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
