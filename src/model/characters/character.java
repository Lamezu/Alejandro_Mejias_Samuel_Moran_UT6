package model.characters;

public abstract class character {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int maxMana;
    protected int currentMana;
    protected String element; // Elemento asociado al personaje (fuego, agua, etc.)
    protected String activeElement; // Elemento con el que está imbuido actualmente
    protected int activeElementTurns; // Turnos restantes con el elemento activo

    public character(String name, int maxHealth, int maxMana, String element) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.element = element;
        this.activeElement = null;
        this.activeElementTurns = 0;
    }
    
    // Método abstracto que implementarán las subclases
    public abstract int attack();
    
    // Métodos comunes
    public void takeDamage(int amount) {
        // Reduce la salud actual, pero no baja de 0
        this.currentHealth = Math.max(0, this.currentHealth - amount);
    }
    
    public void recoverHealth(int amount) {
        // Recupera salud hasta el máximo
        this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);
    }
    
    public void useMana(int amount) {
        // Reduce el maná actual, pero no baja de 0
        this.currentMana = Math.max(0, this.currentMana - amount);
    }
    
    public void recoverMana(int amount) {
        // Recupera maná hasta el máximo
        this.currentMana = Math.min(this.maxMana, this.currentMana + amount);
    }
    
    public void applyElement(String element) {
        // Aplica un elemento al personaje por un turno
        this.activeElement = element;
        this.activeElementTurns = 1; // Dura un turno
    }
    
    public void updateElementState() {
        // Actualiza el estado del elemento activo
        if (activeElementTurns > 0) {
            activeElementTurns--;
            if (activeElementTurns == 0) {
                activeElement = null;
            }
        }
    }
    
    public boolean isAlive() {
        // Verifica si el personaje está vivo
        return currentHealth > 0;
    }
    
    // Getters y setters
    public String getName() {
        return name;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getCurrentMana() {
        return currentMana;
    }
    
    public int getMaxMana() {
        return maxMana;
    }
    
    public String getElement() {
        return element;
    }
    
    public String getActiveElement() {
        return activeElement;
    }

    public int getActiveElementTurns() {
        return activeElementTurns;
    }

    public String setName(String name) {
        this.name = name;
    }

    public int setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public String setElement(String element) {
        this.element = element;
    }

    public String setActiveElement(String activeElement) {
        this.activeElement = activeElement;
    }

    public int setActiveElementTurns(int activeElementTurns) {
        this.activeElementTurns = activeElementTurns;
    }
    
    // Métodos sobreescritos de Object
    @Override
    public String toString() {
        // Representación en texto del personaje
        return name + " - HP: " + currentHealth + "/" + maxHealth + " - Mana: " + currentMana + "/" + maxMana;
    }
    
    @Override
    public boolean equals(Object obj) {
        // Compara dos personajes por nombre
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Character character = (Character) obj;
        return name.equals(character.name);
    }
    
    @Override
    public int hashCode() {
        // Código hash basado en el nombre
        return 31 * name.hashCode();
    }
}
