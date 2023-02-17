package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;

public class AttackCommand implements CommandHandler, BattleCommand {
    int damage;

    @Override
    public void execute(GameServer server, Game game) {
        if (game.isInCombat()) {
            game.getAllMonsters().setHitpoints(game.getAllMonsters().getHitpoints() - damage);
        } else {

        }
    }

    @Override
    public void execute(PlayerCharacter character) {
        damage = character.getDamage();
    }
}
