package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import messages.Colors;

import java.io.IOException;


public class AttackCommand implements BattleHandler { //TODO RANDOMIZE DAMAGE
    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {

        game.getAllMonsters().setHitpoints(game.getAllMonsters().getHitpoints() - character.getMinDamage());
        System.out.println(character.getName().concat(" dealt ") + Colors.BLUE + character.getMinDamage() + Colors.RESET + " of damage!");
        server.broadcastMessage(character.getName().concat(" dealt ") + Colors.BLUE + character.getMinDamage() + Colors.RESET + " of damage!");
        System.out.println(game.getAllMonsters() + " has " + game.getAllMonsters().getHitpoints() + " hitpoints");
    }
}
