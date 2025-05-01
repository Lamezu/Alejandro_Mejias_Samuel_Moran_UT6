package model.characters;

public abstract class Characters {
    protected String name;
    protected int level;
    protected int maxHealth;
    protected int currentHealth;
    protected int maxMana;
    protected int currentMana;
    protected String element;
    protected String activeElement;
    protected int activeElementTurns;
    protected boolean isAlive;

    public Characters(String name, int level) {
        this.name = name;
        this.level = level;
        this.maxHealth = 100 + (level * 10);
        this.currentHealth = maxHealth;
        this.maxMana = 50 + (level * 5);
        this.currentMana = maxMana;
        this.element = "ninguno";
        this.activeElement = null;
        this.activeElementTurns = 0;
        this.isAlive = true;
    }

    public abstract int attack();
    
    public void receiveDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            isAlive = false;
            System.out.println(name + " ha sido derrotado!");
        }
    }

    public void recoverHealth(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
        System.out.println(name + " recupera " + amount + " puntos de salud!");
    }

    public void useMana(int amount) {
        currentMana = Math.max(0, currentMana - amount);
    }

    public void recoverMana(int amount) {
        currentMana = Math.min(currentMana + amount, maxMana);
        System.out.println(name + " recupera " + amount + " puntos de maná!");
    }

    public void resetForBattle() {
        currentHealth = maxHealth;
        currentMana = maxMana;
        activeElement = null;
        activeElementTurns = 0;
        isAlive = true;
    }

    // Métodos para el sistema elemental
    public void applyElement(String element) {
        this.activeElement = element;
        this.activeElementTurns = 3;
        System.out.println(name + " ha sido imbuido con " + element + " por 3 turnos!");
    }

    public void clearActiveElement() {
        if (activeElement != null) {
            System.out.println("El efecto de " + activeElement + " en " + name + " ha terminado");
        }
        this.activeElement = null;
        this.activeElementTurns = 0;
    }

    public void updateElementStatus() {
        if (activeElementTurns > 0) {
            activeElementTurns--;
            if (activeElementTurns <= 0) {
                clearActiveElement();
            }
        }
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getActiveElement() {
        return activeElement;
    }

    public int getActiveElementTurns() {
        return activeElementTurns;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setActiveElement(String element) {
        this.activeElement = element;
    }

    public void setActiveElementTurns(int turns) {
        this.activeElementTurns = turns;
    }

    @Override
    public String toString() {
        return name + " (Nivel " + level + ") - HP: " + currentHealth + "/" + maxHealth + 
               " - Maná: " + currentMana + "/" + maxMana + " - Elemento: " + element;
    }
}