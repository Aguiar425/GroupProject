package messages.commands;

import Game.Game;
import Game.GameServer;

import java.io.IOException;
import java.net.Socket;

public interface CommandHandler {
    void execute(GameServer server, Game game, Socket socket, String name) throws IOException;
}

