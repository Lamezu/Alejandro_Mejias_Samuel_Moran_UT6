package model.characters;

public abstract class meleeCharacter extends character {
    protected int strength;
    protected int resistance;
    
    public meleeCharacter(String name, int maxHealth, int maxMana, String element, int strength, int resistance) {
        super(name, maxHealth, maxMana, element);
        this.strength = strength;
        this.resistance = resistance;
    }
    
    // Método específico para personajes físicos
    public int calculatePhysicalDamage() {
        return strength;
    }
    
    // Método para reducir el daño físico recibido
    public int reduceDamage(int baseDamage) {
        // El daño se reduce según la resistencia
        int reduction = (int)(baseDamage * (resistance / 100.0));
        return Math.max(1, baseDamage - reduction); // Siempre recibe al menos 1 de daño
    }
    
    // Getters
    public int getStrength() {
        return strength;
    }
    
    public int getResistance() {
        return resistance;
    }
    
    // Setters
    public void setStrength(int strength) {
        this.strength = strength;
    }
    
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
}
