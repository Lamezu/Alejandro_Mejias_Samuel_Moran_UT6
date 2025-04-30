package model.characters;

public abstract class PhysicalCharacter extends Characters {
    protected int strength;
    protected int resistance;
    public PhysicalCharacter(String name, int level) {
        super(name, level);
        this.maxHealth = 120 + (level * 25);
        this.currentHealth = maxHealth;
        this.maxMana = 40 + (level * 5);
        this.currentMana = maxMana;
        this.strength = 10 + (level * 2);
        this.resistance = 8 + level;
    }
    /**
    Método específico para personajes físicos - calcula el daño base
    */
    public int calculatePhysicalDamage() {
        // Fórmula base para calcular daño físico
        int baseDamage = strength + (int)(Math.random() * (strength / 2));
        return baseDamage;
    }
    /**
    Método para reducir el daño físico recibido
    */
    public int reduceDamage(int baseDamage) {
        // El daño se reduce según la resistencia
        double reductionFactor = resistance / 100.0; 
        int reduction = (int)(baseDamage * reductionFactor);
        return Math.max(1, baseDamage - reduction); // Siempre recibe al menos 1 de daño
    }
    // Getters y setters
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getResistance() {
        return resistance;
    }
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }
    @Override
    public String toString() {
        return super.toString() + " - STR: " + strength + " - RES: " + resistance;
    }
}