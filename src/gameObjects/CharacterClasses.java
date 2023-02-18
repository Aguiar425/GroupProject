package gameObjects;

public enum CharacterClasses {
    ROGUE(50, 3, 12),
    WARRIOR(100, 5, 10),
    MAGE(30, 10, 15);

    private final int startingHitpoints;
    private final int minDamage;
    private final int maxDamage;

    CharacterClasses(int startingHitpoints, int minDamage, int maxDamage) {
        this.startingHitpoints =  startingHitpoints;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public int getStartingHitpoints() {
        return startingHitpoints;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }
}
