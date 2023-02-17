package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;

public interface BattleHandler {
    void execute(GameServer server, Game game, PlayerCharacter character);
}
