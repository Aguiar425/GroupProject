package messages.commands;

import Game.Game;
import Game.GameServer;

import java.io.IOException;
import java.net.Socket;

public class HelpCommand implements CommandHandler {
    @Override
    public void execute(GameServer server, Game game, Socket socket, String name) throws IOException {

        server.writeAndSend(socket, "These are the available commands");
        server.writeAndSend(socket, "/help  -> Lists all commands");
        //server.writeAndSend(socket, "/save  -> Saves the game");
        server.writeAndSend(socket, "/exit  -> Leaves the game");
        server.writeAndSend(socket, "/back  -> Returns the party to the previous screen");
        server.writeAndSend(socket, "/list  -> Lists all players in the game");
        server.writeAndSend(socket, "/inv  -> Shows the party's inventory");
        server.writeAndSend(socket, "/stats  -> Shows your character stats");

    }
}
