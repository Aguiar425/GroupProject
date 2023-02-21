package gameObjects;

public enum CharacterClasses {
    ROGUE(65, 3, 12, "Rogue"),
    WARRIOR(100, 5, 10, "Warrior"),
    MAGE(30, 10, 15, "Mage");

    private final int startingHitpoints;
    private final int minDamage;
    private final int maxDamage;
    private final String description;

    CharacterClasses(int startingHitpoints, int minDamage, int maxDamage, String description) {
        this.startingHitpoints = startingHitpoints;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
