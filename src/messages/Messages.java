package messages;

import java.awt.*;

public abstract class Messages {
    public static final String GAME_STARTED = "Game.Game started";
    public static final String BATTLE_STARTED = "The party has encountered a %s";
    public static final String TURN_RIGHT = "The party turns right";
    public static final String TURN_LEFT = "The party turns left";
    public static final String FORWARD = "The party marches onward";
    public static final String BACK = "The party turns back";
    public static final String OPEN_DOOR = "The party opens the door ahead of them";
    public static final String TAKE_DAMAGE = "%s takes %d damage";
    public static final String HEAL = "%s heals for %d damage";
    public static final String DEAL_DAMAGE = "%s deals %d damage to %s";
    public static final String BATTLE_WIN = "The party defeats the enemy";
    public static final String VICTORY = "CONGRATULATIONS! The party has won the game";
    public static final String BATTLE = "GAME OVER! Your journey ends here";
    public static final String USER_LEFT = "%s has cowardly abandoned the party...";
    public static final String ASK_FOR_USERNAME = "What is your character's name?";
    public static final String ASK_FOR_CLASS = "What is your class?";
    public static final String AVAILABLE_CLASSES = Colors.PINK + "<R>ogue        " + Colors.RED + "<W>arrior         "  +Colors.BLUE + "<M>age";
    public static final String INVALID_INPUT = "Please insert a valid input";
    public static final String USER_JOINED = "%s has joined the party. Waiting for more players...";
    public static final String PLAYER_LIMIT = "The adventuring party is full";
    public static final String NOT_ENOUGH_GOLD = "Not enough gold to buy this item";
}
