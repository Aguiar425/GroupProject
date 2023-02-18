package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;

import java.io.IOException;

public class DefendCommand implements BattleHandler {

    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {
        character.setDefending(true);
        server.broadcastMessage(character.getName().concat(" is defending"));
    }
}
