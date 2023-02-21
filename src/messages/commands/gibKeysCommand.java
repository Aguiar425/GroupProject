package messages.commands;

import Game.Game;
import Game.GameServer;

import java.io.IOException;
import java.net.Socket;

public class gibKeysCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {
        game.setKeyCounter(3);
        server.writeAndSend(socket, "KEYS!!!");
    }
}
