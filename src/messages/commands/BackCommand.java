package messages.commands;

import Game.Game;
import Game.GameServer;
import messages.Colors;
import messages.Messages;

import java.io.IOException;
import java.net.Socket;

public class BackCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) {
        try {
            if (game.getCurrentRoom() == 2 || game.getCurrentRoom() == 3) {
                System.out.println("Party left room: " + game.getCurrentRoom());
                server.broadcastMessage(game.printChapterOne());
                game.setCurrentRoom(1);
            } else if (game.getCurrentRoom() == 4) {
                //TODO add room 5 (final room)
                System.out.println("Party left room: " + game.getCurrentRoom());
                server.broadcastMessage(game.printChapterThree());
                game.setCurrentRoom(3);
            } else if (game.getCurrentRoom() == 10) {
                game.getSound().getSoundLoopVar().stop();
                System.out.println("Party left room: " + game.getCurrentRoom());
                server.broadcastMessage(game.printChapterTwo());
                game.setCurrentRoom(2);
            } else if (game.getCurrentRoom() == 21) {
                game.getSound().getSoundLoopVar().stop();
                game.getSound().getDungeonSoundLoopVar().start();
                System.out.println("Party left room: " + game.getCurrentRoom());
                server.broadcastMessage(game.printChapterOne());
                game.setCurrentRoom(1);
            } else if (game.getCurrentRoom() == 22) {
                game.getSound().getSoundLoopVar().stop();
                System.out.println("Party left room: " + game.getCurrentRoom());
                server.broadcastMessage(game.printChapterTwo());
                game.setCurrentRoom(2);
            } else if (game.getCurrentRoom() == 23) {
                game.getSound().getSoundLoopVar().stop();
                System.out.println("Party left room: " + game.getCurrentRoom());
                server.broadcastMessage(game.printChapterFour());
                game.setCurrentRoom(4);
            } else if (game.getCurrentRoom() == 24) {
                server.broadcastMessage(Messages.RUN_LAST_BATTLE);
            } else if (game.getCurrentRoom() == 31) {
                server.broadcastMessage(game.printChapterThree());
            } else if (game.getCurrentRoom() == 32) {
                server.broadcastMessage(game.printBattleThree());
            } else {
                System.out.println("Party tried to leave room: " + game.getCurrentRoom() + " KEK");
                server.broadcastMessage(Colors.RED + "You can't go back, there is no escape".toUpperCase() + Colors.RESET);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
