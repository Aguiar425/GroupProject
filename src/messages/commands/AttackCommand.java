package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;

public class AttackCommand implements CommandHandler, BattleCommand {
    
    @Override
    public void execute(GameServer server, Game game) {
        if (game.isInCombat()) {
            game.getAllMonsters().setHitpoints(game.getAllMonsters().getHitpoints() - player.getDamage());
        } else {

        }
    }

    @Override
    public void execute(PlayerCharacter character) {

    }
}
