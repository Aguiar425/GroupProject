package gameObjects;

public enum CharacterClasses {
    ROGUE(50, 3),
    WARRIOR(100, 5),
    MAGE(30, 10);

    private final int startingHitpoints;
    private final int startingDamage;

    CharacterClasses(int startingHitpoints, int startingDamage) {
        this.startingHitpoints =  startingHitpoints;
        this.startingDamage = startingDamage;
    }

    public int getStartingHitpoints() {
        return startingHitpoints;
    }

    public int getStartingDamage() {
        return startingDamage;
    }
}
