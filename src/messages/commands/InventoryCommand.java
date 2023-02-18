package messages.commands;

import Game.Game;
import Game.GameServer;

import java.io.IOException;
import java.net.Socket;

public class InventoryCommand implements CommandHandler{
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {
        server.broadcastMessage("PARTY INVENTORY: ");
        server.broadcastMessage("-" + game.getGold() + " gold");
        server.broadcastMessage("-" + game.getKeyCounter() + " keys");
        server.broadcastMessage("-" + game.getHealingPotions() + " potions");
    }
}
