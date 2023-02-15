package gameObjects;

public class Character {

    private CharacterClasses characterClass;
    private String name;
    private int hitpoints;
    private int damage;
    private Boolean isLeader;

    public Character(CharacterClasses characterClass, String name) {
        this.characterClass = characterClass;
        this.name = name;
        this.isLeader = false;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }
}
