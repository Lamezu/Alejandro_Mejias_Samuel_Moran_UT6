package model.characters;

public abstract class Characters {
    protected String name;
    protected int level;
    protected int maxHealth;
    protected int currentHealth;
    protected int maxMana;
    protected int currentMana;
    protected String element; // Elemento asociado al personaje (fuego, agua, etc.)
    protected String activeElement; // Elemento con el que está imbuido actualmente
    protected int activeElementTurns; // Turnos restantes con el elemento activo

    public Characters(String name, int level) {
        this.name = name;
        this.level = level;
        this.maxHealth = 100 + (level * 20);
        this.currentHealth = maxHealth;
        this.maxMana = 50 + (level * 10);
        this.currentMana = maxMana;
        this.element = null;
        this.activeElement = null;
        this.activeElementTurns = 0;
    }
    // Método abstracto que implementarán las subclases
    public abstract int attack();
    /**
    Recibe daño y reduce la salud actual
    */
    public void receiveDamage(int amount) {
        this.currentHealth = Math.max(0, this.currentHealth - amount);
        System.out.println(name + " receives " + amount + " damage! HP: " + currentHealth + "/" + maxHealth);
    }
    /**
    Recupera salud hasta el máximo
    */
    public void recoverHealth(int amount) {
        int previousHealth = this.currentHealth;
        this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);
        int actualRecovered = this.currentHealth - previousHealth;
        System.out.println(name + " recovers " + actualRecovered + " health! HP: " + currentHealth + "/" + maxHealth);
    }
    /**
    Consume maná
    */
    public void useMana(int amount) {
        if (this.currentMana >= amount) {
            this.currentMana -= amount;
            System.out.println(name + " uses " + amount + " mana. Remaining: " + currentMana + "/" + maxMana);
        } else {
            System.out.println(name + " doesn't have enough mana!");
        }
    }
    /**
    Recupera maná hasta el máximo
    */
    public void recoverMana(int amount) {
        int previousMana = this.currentMana;
        this.currentMana = Math.min(this.maxMana, this.currentMana + amount);
        int actualRecovered = this.currentMana - previousMana;
        System.out.println(name + " recovers " + actualRecovered + " mana! Mana: " + currentMana + "/" + maxMana);
    }
    /**
    Aplica un elemento al personaje por un turno
    */
    public void applyElement(String element) {
        if (element != null) {
            this.activeElement = element;
            this.activeElementTurns = 1; // Dura un turno
            System.out.println(name + " is now imbued with " + element + " element!");
        }
    }
    /**
    Actualiza el estado del elemento activo
    */
    public void updateElementStatus() {
        if (activeElementTurns > 0) {
            activeElementTurns--;
        if (activeElementTurns == 0) {
            System.out.println(name + " is no longer imbued with " + activeElement + " element!");
            activeElement = null;
            }
        }
    }
    /**
    Verifica si el personaje está vivo
    */
    public boolean isAlive() {
        return currentHealth > 0;
    }
    // Getters y setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    public int getCurrentMana() {
        return currentMana;
    }
    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
    public int getMaxMana() {
        return maxMana;
    }
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
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
    public void setActiveElement(String activeElement) {
        this.activeElement = activeElement;
    }
    public int getActiveElementTurns() {
        return activeElementTurns;
    }
    public void setActiveElementTurns(int activeElementTurns) {
        this.activeElementTurns = activeElementTurns;
    }
    @Override
    public String toString() {
        return name + " (Lvl " + level + ") - HP: " + currentHealth + "/" + maxHealth + 
        " - Mana: " + currentMana + "/" + maxMana + 
        (element != null ? " - Element: " + element : "");
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Characters character = (Characters) obj;
        return name.equals(character.name);
    }
    @Override
    public int hashCode() {
        return 31 * name.hashCode();
    }
}