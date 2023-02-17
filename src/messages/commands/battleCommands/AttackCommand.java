package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;


public class AttackCommand implements BattleHandler {
    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) {
        game.getAllMonsters().setHitpoints(game.getAllMonsters().getHitpoints() - character.getDamage());
        System.out.println("dealt " + character.getDamage());
    }
}
