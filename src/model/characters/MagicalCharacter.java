package model.characters;

public abstract class MagicalCharacter extends Character {
    protected int intelligence;
    protected int wisdom;
    
    public MagicalCharacter(String name, int maxHealth, int maxMana, String element, int intelligence, int wisdom) {
        super(name, maxHealth, maxMana, element);
        this.intelligence = intelligence;
        this.wisdom = wisdom;
    }
    
    // Método específico para personajes mágicos
    public int calculateMagicalDamage() {
        // Fórmula base para calcular daño mágico
        return intelligence;
    }
    
    // Método para regenerar maná pasivamente
    public void passiveManaRegen() {
        int regeneratedAmount = wisdom / 5;
        recoverMana(regeneratedAmount);
    }
    
    public int getIntelligence() {
        return intelligence;
    }
    
    public int getWisdom() {
        return wisdom;
    }
    
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    
    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }
}
