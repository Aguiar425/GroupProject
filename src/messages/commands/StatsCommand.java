package messages.commands;

import Game.Game;
import Game.GameServer;
import gameObjects.PlayerCharacter;
import gameObjects.RandomNumber;

import java.io.IOException;
import java.net.Socket;

public class StatsCommand implements CommandHandler{  //TODO ONLY PRINT ONE PLAYER - FILTER BY CHARACTER NAME
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {
        server.writeAndSend(socket, "Your stats: ");
        for (PlayerCharacter pc :
                game.getParty()) {
            server.writeAndSend(socket, "Name: " + pc.getName());
            server.writeAndSend(socket, "HP: " + pc.getHitpoints());
            server.writeAndSend(socket, "Damage: " + pc.getMinDamage() + " - " + pc.getMaxDamage());
            server.writeAndSend(socket, "Strength: " + RandomNumber.randomizer(3, 69));
            server.writeAndSend(socket, "Dexterity : " + RandomNumber.randomizer(3, 69));
            server.writeAndSend(socket, "Sex Appeal : 0" );
        }
    }
}
