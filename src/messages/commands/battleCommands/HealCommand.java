package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import messages.Colors;
import messages.Messages;

import java.io.IOException;

public class HealCommand implements BattleHandler {

    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {
        if (game.getHealingPotions() == 0) {
            server.broadcastMessage(Messages.NO_POTIONS);
        } else {
            if (character.getHitpoints() == character.getMaxHitpoints()) {
                server.broadcastMessage(Messages.MAX_HP_HEAL);
            } else {
                int amountHealed = character.getMaxHitpoints() - character.getHitpoints();
                character.setHitpoints(character.getMaxHitpoints());
                game.setHealingPotions(game.getHealingPotions() - 1);
                server.broadcastMessage(Messages.FULL_HEAL + Colors.BLUE + amountHealed + Colors.RESET + " Hit Points");

            }
        }
    }
}
