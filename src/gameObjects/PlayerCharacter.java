package gameObjects;

public class PlayerCharacter {

    private CharacterClasses characterClass;
    private String name;
    private int hitpoints;
    private int maxHitpoints;
    private int minDamage;
    private int maxDamage;
    private boolean isDefending;

    public PlayerCharacter(CharacterClasses characterClass, String name) {
        this.characterClass = characterClass;
        this.name = name;
        this.hitpoints = characterClass.getStartingHitpoints();
        this.maxHitpoints = hitpoints;
        this.minDamage = characterClass.getMinDamage();
        this.maxDamage = characterClass.getMaxDamage();
        this.isDefending = false;
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

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
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

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
}
