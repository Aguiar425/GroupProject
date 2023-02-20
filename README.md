# GroupProject


## Introduction

This is a game created in Java only that resembles "Choose your own adventure" Books.
In this game you can create as many players as you can (although it has been balanced with two players in mind).

***

## How to play

To play this game you first need to start the game server by running the main method on the "GameLauncher" class.

This will then ask you how many players will be playing. After you insert how many players you wish to have, you need to initialize the Players by running
the main method on the "PlayerLauncher" class as many times as you have players.
Afterwards, you may write your character's name and class.

#### Moving

To move around the game map you will need to write the character "@" followed by selecting the specified command displayed on each screen 
For example, if you want to move Forward, you must type "@F".
To move back to the previous screen, you can use the "/back" command.

#### Player Classes

During character creation, you may choose between three different classes.
- R -> Rogue: A nimble fighter that has a lower attack power than a Warrior or a Mage, but has the ability to unlock certain doors even if you don't
              possess the key for it.
- W -> Warrior: A bulky fighter that boasts higher hitpoints than any other class.
- M -> Mage: A frail arcane caster. What they lacks in hitpoints, they make up for in steady, high damage.

#### Battle

During your adventures you will encounter various aggressive monsters. These encounters will start a battle.
To battle said monsters you will need to perform battle specific commands (listed below).


#### Shop

In a certain room, you may find a friendly old man that will sell you valuable goods to help you in your journey.
To interact with the shops you will need to use these commands:
- @K -> Buys a key that may or may not be helpful to you.
- @P -> Buys a healing potion.

***

## List of available commands

#### Outside of battle commands

While outside of battle, apart from the movement commands, you may use these aditional commands:
- /help -> Lists all the game commands available.
- /list -> Lists all the players currrently in the game.
- /exit -> Leave the game.
- /inv -> Shows the party's inventory.
- /stats -> Shows your character's current name, class and battle related statistics.
- /heal -> Uses a potion to fully heal the player character.
- /back -> Returns the party to the previous screen.

#### Battle Commands

These are commands only usable while in battle:
- /attack  -> Attacks the monster. The damage will be a randomized value between your character's minimum and maximum damage.
- /defend -> You will skip your turn but take half the damage should your character be attacked.
- /heal -> Spend a turn drinking a healing potion that will return your HP to its maximum value.
- /run -> Spend a turn attempting to run away from the current battle.



### Credits 

This project was created by MindSwap 2023 students:
- Augusto Faria - @GutoFaria365
- Miguel Aguiar - @Aguiar425
- Miguel Silva - @reinbeers

