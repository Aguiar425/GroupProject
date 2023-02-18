package messages.commands.battleCommands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import messages.Colors;

import java.io.IOException;


public class AttackCommand implements BattleHandler {
    @Override
    public void execute(GameServer server, Game game, PlayerCharacter character) throws IOException {

        game.getAllMonsters().setHitpoints(game.getAllMonsters().getHitpoints() - character.getDamage());
        System.out.println(character.getName().concat(" dealt ") + Colors.BLUE + character.getDamage() + Colors.RESET + " of damage!");
        server.broadcastMessage(character.getName().concat(" dealt ") + Colors.BLUE + character.getDamage() + Colors.RESET + " of damage!");
        System.out.println(game.getAllMonsters() + " has " + game.getAllMonsters().getHitpoints() + " hitpoints");
    }
}
