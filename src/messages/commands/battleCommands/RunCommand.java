package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import gameObjects.RandomNumber;
import messages.Messages;

import java.io.IOException;

public class RunCommand implements BattleHandler {
    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {
        int chance = RandomNumber.randomizer(1, 2);

        switch (chance) {
            case 1:
                game.setInCombat(false);
                server.broadcastMessage(Messages.RUN_SUCCESS);
                break;
            case 2:
                server.broadcastMessage(Messages.RUN_FAIL);
                break;
        }
    }
}

