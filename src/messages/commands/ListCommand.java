package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;

import java.io.IOException;
import java.net.Socket;

public class ListCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {

        for (PlayerCharacter pc :
                game.getParty()) {
            server.writeAndSend(socket, pc.getName() + " - " + pc.getCharacterClass().getDescription());
        }
    }
}
