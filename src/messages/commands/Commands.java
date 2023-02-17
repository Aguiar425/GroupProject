package messages.commands;

public enum Commands {

    HELP("/help", new HelpCommand()),  //Lists all commands
    SAVE("/save", new SaveCommand()),
    EXIT("/exit", new ExitCommand()),
    BACK("/back", new BackCommand()),  //This command returns the party to the previous screen
    LIST("/list", new ListCommand());//Lists all players

    private String description;
    private CommandHandler command;

    Commands(String description, CommandHandler command) {
        this.description = description;
        this.command = command;

    }

    public static Commands getCommand(String description) {
        for (Commands command : values()) {
            if (description.equals(command.description)) {
                return command;
            }
        }
        return null;
    }

    public CommandHandler getHandler() {
        return command;
    }
}