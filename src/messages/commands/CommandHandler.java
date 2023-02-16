package messages.commands;

import Game.Game;
import Game.GameServer;

public interface CommandHandler {
    void execute(GameServer server, Game game);
}

