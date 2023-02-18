package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;

import java.io.IOException;

public interface BattleHandler {
    void execute(GameServer server, Game game, PlayerCharacter character) throws IOException;
}
