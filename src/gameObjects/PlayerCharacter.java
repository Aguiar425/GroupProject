package gameObjects;

public class PlayerCharacter {

    private CharacterClasses characterClass;
    private String name;
    private int hitpoints;
    private int maxHitpoints;
    private int damage;
    private Boolean isLeader;
    private boolean isDefending;

    public PlayerCharacter(CharacterClasses characterClass, String name) {
        this.characterClass = characterClass;
        this.name = name;
        this.hitpoints = characterClass.getStartingHitpoints();
        this.maxHitpoints = hitpoints;
        this.damage = characterClass.getStartingDamage();
        this.isLeader = false;
        this.isDefending = false;
    }

    public PlayerCharacter() {
        this.characterClass = null;
        this.name = null;
        this.hitpoints = 0;
        this.damage = 0;
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

    public CharacterClasses getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClasses characterClass) {
        this.characterClass = characterClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
    }

    public boolean isDefending() {
        return isDefending;
    }

    public void setDefending(boolean defending) {
        isDefending = defending;
    }
}
