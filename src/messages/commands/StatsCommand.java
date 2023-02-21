package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import gameObjects.RandomNumber;
import messages.Colors;

import java.io.IOException;
import java.net.Socket;


public class StatsCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {
        server.writeAndSend(socket, "Your stats: ");

        PlayerCharacter pc = game.getParty().stream()
                .filter(character -> character.getName().equals(name))
                .toList()
                .get(0);

        server.writeAndSend(socket, "Name: " + Colors.YELLOW + pc.getName() + Colors.RESET);
        server.writeAndSend(socket, "Class: " + Colors.CYAN + pc.getCharacterClass().getDescription() + Colors.RESET);
        server.writeAndSend(socket, "HP: " + Colors.RED + pc.getHitpoints() + Colors.RESET + "/" + Colors.BLUE + pc.getMaxHitpoints() + Colors.RESET);
        server.writeAndSend(socket, "Damage: " + Colors.RED + pc.getMinDamage() + Colors.RESET + " - " + Colors.BLUE + pc.getMaxDamage() + Colors.RESET);
        server.writeAndSend(socket, "Strength: " + RandomNumber.randomizer(3, 69));
        server.writeAndSend(socket, "Dexterity : " + RandomNumber.randomizer(3, 69));
        server.writeAndSend(socket, "Sex Appeal : 0");
    }
}
