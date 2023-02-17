package gameObjects;

public class Monster {

    private MonsterClasses monsterClass;
    private int hitpoints;
    private int minDamage;
    private int maxDamage;
    private Boolean isAlive;

    public Monster(MonsterClasses monsterClass) {
        this.monsterClass = monsterClass;
        this.hitpoints = monsterClass.getHitpoints();
        this.minDamage = monsterClass.getMinDamage();
        this.maxDamage = monsterClass.getMaxDamage();
        this.isAlive = true;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
