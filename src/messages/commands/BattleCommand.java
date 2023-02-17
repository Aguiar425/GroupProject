package messages.commands;

import gameObjects.PlayerCharacter;

public interface BattleCommand {
    void execute(PlayerCharacter character);
}
