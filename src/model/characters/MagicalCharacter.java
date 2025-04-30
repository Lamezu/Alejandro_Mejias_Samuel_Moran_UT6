package model.characters;

public abstract class MagicalCharacter extends Characters {
    protected int intelligence;
    protected int wisdom;
    public MagicalCharacter(String name, int level) {
        super(name, level);
        this.maxHealth = 80 + (level * 15);
        this.currentHealth = maxHealth;
        this.maxMana = 80 + (level * 15);
        this.currentMana = maxMana;
        this.intelligence = 10 + (level * 2);
        this.wisdom = 8 + level;
    }
    /**
    Método específico para personajes mágicos - calcula el daño mágico base
    */
    public int calculateMagicalDamage() {
        // Fórmula base para calcular daño mágico
        int baseDamage = intelligence + (int)(Math.random() * (intelligence / 2));
        return baseDamage;
    }
    /**
    Método para regenerar maná pasivamente
    */
    public void passiveManaRegen() {
        int regeneratedAmount = wisdom / 5;
        recoverMana(regeneratedAmount);
        System.out.println(getName() + " passively regenerates " + regeneratedAmount + " mana from wisdom!");
    }
    // Getters y setters
    public int getIntelligence() {
        return intelligence;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    public int getWisdom() {
        return wisdom;
    }
    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }
    @Override
    public String toString() {
        return super.toString() + " - INT: " + intelligence + " - WIS: " + wisdom;
    }
}