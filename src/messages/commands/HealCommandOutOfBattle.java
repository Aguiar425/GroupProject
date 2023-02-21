package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import messages.Colors;
import messages.Messages;

import java.io.IOException;
import java.net.Socket;

public class HealCommandOutOfBattle implements CommandHandler {
    //TODO FIX METHOD
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {
        PlayerCharacter character = null;
        for (PlayerCharacter pc : game.getParty()) {
            if (pc.getName().equals(name)) {
                character = pc;
            }
        }
        if (game.getHealingPotions() == 0) {
            server.writeAndSend(socket, Messages.NO_POTIONS);
        } else {
            if (character.getHitpoints() == character.getMaxHitpoints()) {
                server.writeAndSend(socket, Messages.MAX_HP_HEAL);
            } else {
                int amountHealed = character.getMaxHitpoints() - character.getHitpoints();
                character.setHitpoints(character.getMaxHitpoints());
                game.setHealingPotions(game.getHealingPotions() - 1);
                server.writeAndSend(socket, Messages.FULL_HEAL + Colors.BLUE + amountHealed + Colors.RESET + " Hit Points");
            }
        }

    }
}
