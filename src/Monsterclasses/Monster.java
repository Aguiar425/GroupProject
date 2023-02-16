package Monsterclasses;

public class Monster {
    private MonsterClasses monsterClass;
    private int hitpoints;
    private int damage;

    public Monster(MonsterClasses monsterClass, int hitpoints, int damage) {
        this.monsterClass = monsterClass;
        this.hitpoints = getHitpoints();
        this.damage = getDamage();
    }

    public int getHitpoints() {
        return hitpoints;
    }
    public int getDamage() {
        return damage;
    }
}