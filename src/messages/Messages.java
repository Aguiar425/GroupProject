package messages;

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
    public static final String AVAILABLE_CLASSES = Colors.PINK + "<R>ogue        " + Colors.RED + "<W>arrior         " + Colors.BLUE + "<M>age";
    public static final String INVALID_INPUT = "Please insert a valid input";
    public static final String USER_JOINED = "%s has joined the party. Waiting for more players...";
    public static final String PLAYER_LIMIT = "The adventuring party is full";
    public static final String NO_SPAM = Colors.RED + "Too many inputs, wait a few seconds!" + Colors.RESET;
    public static final String NOT_ENOUGH_GOLD = "Not enough gold to buy this item";
    public static final String YOU_ALREADY_ATTACKED = Colors.RED + "You already attacked this turn!" + Colors.RESET;
    public static final String RUN_LAST_BATTLE = Colors.RED + "AH AH AH. YOU CAN'T ESCAPE FROM ME COWARDS!" + Colors.RESET;
    public static final String NO_POTIONS = "You have no more potions, maybe you should have bought some more at the potion seller!!";
    public static final String MAX_HP_HEAL = "Why would you try to heal at max hp??";
    public static final String RUN_SUCCESS = Colors.BLUE + "You have successfully escaped the battle. \nPlease use the /back to return" + Colors.RESET;
    public static final String RUN_FAIL = Colors.RED + "You have failed to escape the battle" + Colors.RESET;
    public static final String KEY_BOUGHT = "You have bought a door key.";
    public static final String KEY_ALREADY_BOUGHT = "The key is already in your possession";
    public static final String POTION_BOUGHT = "You have bought a healing potion";
    public static final String CHEST_ALREADY_OPENED = "This chest is empty.\nGo back to continue your adventure";
    public static final String NO_BATTLE_COMMAND = Colors.RED + "NO SUCH COMMAND EXISTS" + Colors.RESET;
}
