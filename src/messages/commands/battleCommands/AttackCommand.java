package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import gameObjects.RandomNumber;
import messages.Colors;

import java.io.IOException;


public class AttackCommand implements BattleHandler {
    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {

        int damage = RandomNumber.randomizer(character.getMinDamage(), character.getMaxDamage());

        game.getAllMonsters().setHitpoints(game.getAllMonsters().getHitpoints() - damage);
        System.out.println(character.getName().concat(" dealt ") + Colors.BLUE + damage + Colors.RESET + " of damage!");
        server.broadcastMessage(character.getName().concat(" dealt ") + Colors.BLUE + damage + "/???" + Colors.RESET + " of damage!");
        System.out.println(game.getAllMonsters() + " has " + game.getAllMonsters().getHitpoints() + " hitpoints");
    }
}
