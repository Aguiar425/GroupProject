package gameObjects;

public enum LootTable {

    BATTLE_ONE(100, 0, 0, "100 Gold"),
    BATTLE_TWO(0, 1, 2, "1 key, 2 potions"),
    BATTLE_THREE(100, 1, 3, "100 gold, 1 key, 3 potions"),
    BATTLE_FINAL(0, 0, 0, "lol nothing");

    private int gold;
    private int keys;
    private int potions;
    private String description;

    LootTable(int gold, int keys, int potions, String description) {
        this.gold = gold;
        this.keys = keys;
        this.potions = potions;
        this.description = description;
    }

    public int getLootGold() {
        return gold;
    }

    public int getLootKeys() {
        return keys;
    }

    public int getLootPotions() {
        return potions;
    }

    public String getLootDescription() {
        return description;
    }
}
