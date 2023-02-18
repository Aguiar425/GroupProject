package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import gameObjects.RandomNumber;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StatsCommand implements CommandHandler{
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {
        server.writeAndSend(socket, "Your stats: ");

        PlayerCharacter pc = game.getParty().stream()
                .filter(character -> character.getName().equals(name))
                .toList()
                .get(0);

            server.writeAndSend(socket, "Name: " + pc.getName());
            server.writeAndSend(socket, "HP: " + pc.getHitpoints());
            server.writeAndSend(socket, "Damage: " + pc.getMinDamage() + " - " + pc.getMaxDamage());
            server.writeAndSend(socket, "Strength: " + RandomNumber.randomizer(3, 69));
            server.writeAndSend(socket, "Dexterity : " + RandomNumber.randomizer(3, 69));
            server.writeAndSend(socket, "Sex Appeal : 0" );
    }
}
