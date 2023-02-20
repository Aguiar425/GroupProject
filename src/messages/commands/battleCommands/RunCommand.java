package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import gameObjects.RandomNumber;
import messages.Colors;
import messages.Messages;

import java.io.IOException;

public class RunCommand implements BattleHandler {
    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {
        int chance = RandomNumber.randomizer(1, 2);
        if (game.getCurrentRoom() == 24) {
            server.broadcastMessage(Colors.RED + "                      HAHAHAHAH, YOU THINK YOU CAN ESCAPE ME???????" + Colors.RESET);
            server.broadcastMessage(Colors.RED + "                                      THINK AGAIN" + Colors.RESET);
        }
        switch (chance) {
            case 1:
                game.setInCombat(false);
                server.broadcastMessage(Messages.RUN_SUCCESS);
                server.notifyAll();
                GameServer.setPlayerTurn(0);
                GameServer.setDeadPlayers(0);
                for (PlayerCharacter pc : game.getParty()) {
                    if (pc.getHitpoints() == 0) {
                        pc.setHitpoints(1);
                    }
                }
                break;
            case 2:
                server.broadcastMessage(Messages.RUN_FAIL);
                break;
        }
    }
}

