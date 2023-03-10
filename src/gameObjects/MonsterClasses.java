package gameObjects;

public enum MonsterClasses {

    BUG(50, 3, 7, LootTable.BATTLE_ONE),
    ELF(75, 5, 10, LootTable.BATTLE_TWO),
    GRIFFIN(100, 7, 13, LootTable.BATTLE_THREE),
    FINAL(300, 10, 15, LootTable.BATTLE_FINAL);

    private int hitpoints;
    private int minDamage;
    private int maxDamage;
    private LootTable loot;

    MonsterClasses(int hitpoints, int minDamage, int maxDamage, LootTable loot) {
        this.hitpoints = hitpoints;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.loot = loot;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public LootTable getLoot() {
        return loot;
    }
}
