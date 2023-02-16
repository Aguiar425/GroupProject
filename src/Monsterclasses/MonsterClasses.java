package Monsterclasses;

public enum MonsterClasses {
    Nix(120, 20),
    Caliban(300, 2),
    Gallic(15, 70);

    private int startingHitpoints;
    private int startingDamage;

    MonsterClasses(int hitpoints, int damage){
        this.startingHitpoints = hitpoints;
        this.startingDamage = damage;
    }
    public int getStartingHitpoints() {
        return startingHitpoints;
    }
    public int getStartingDamage() {
        return startingDamage;
    }
}
