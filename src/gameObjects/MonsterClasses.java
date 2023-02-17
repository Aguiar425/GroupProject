package gameObjects;

public enum MonsterClasses {

    BUG(50, 3, 7),
    ELF(75, 5, 10),
    GRIFFIN(100, 7, 13),
    FINAL(125, 10, 15);

    private int hitpoints;
    private int minDamage;
    private int maxDamage;

    MonsterClasses(int hitpoints, int minDamage, int maxDamage) {
        this.hitpoints = hitpoints;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
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
}
