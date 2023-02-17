package messages.commands.battleCommands;

public enum BattleCommands {

    ATTACK("/attack", new AttackCommand()),
    DEFEND("/defend", new DefendCommand()),
    HEAL("/heal", new HealCommand()),
    RUN("/run", new RunCommand());

    private String description;
    private BattleHandler command;

    BattleCommands(String description, BattleHandler command) {
        this.description = description;
        this.command = command;

    }

    public static BattleCommands getCommand(String description) {
        for (BattleCommands command : values()) {
            if (description.equals(command.description)) {
                return command;
            }
        }
        return null;
    }

    public BattleHandler getBattleHandler() {
        return command;
    }
}