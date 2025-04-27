package view;

import model.characters.Character;
import model.battle.Element;

/**
 * Panel para mostrar la información de un personaje en la interfaz de batalla
 */
public class CharacterPanel {
    private Character character;
    
    public CharacterPanel() {
        this.character = null;
    }
    
    /**
     * Establece el personaje a mostrar
     */
    public void setCharacter(Character character) {
        this.character = character;
    }
    
    /**
     * Muestra la información completa del personaje
     */
    public void displayCharacterStatus() {
        if (character == null) {
            return;
        }
        
        System.out.println(character.getName() + " (HP: " + character.getCurrentHealth() + "/" + character.getMaxHealth() +
                         ", Mana: " + character.getCurrentMana() + "/" + character.getMaxMana() + ")");
        showProgressBar("HP", character.getCurrentHealth(), character.getMaxHealth(), 20);
        showProgressBar("Mana", character.getCurrentMana(), character.getMaxMana(), 20);
        
        if (character.getActiveElement() != null) {
            System.out.println("Elemento activo: " + character.getActiveElement());
        }
    }
    
    /**
     * Muestra una barra de progreso visual
     */
    private void showProgressBar(String label, int currentValue, int maxValue, int length) {
        int filled = (int) ((double) currentValue / maxValue * length);
        
        String startColor = "";
        String endColor = "";
        
        if (label.equals("HP")) {
            startColor = "\u001B[32m"; // Verde para HP
        } else if (label.equals("Mana")) {
            startColor = "\u001B[34m"; // Azul para Mana
        }
        endColor = "\u001B[0m";
        
        System.out.print(label + ": [");
        System.out.print(startColor);
        
        for (int i = 0; i < length; i++) {
            if (i < filled) {
                System.out.print("█");
            } else {
                System.out.print(" ");
            }
        }
        
        System.out.print(endColor);
        System.out.println("] " + currentValue + "/" + maxValue);
    }
    
    /**
     * Muestra las estadísticas del personaje
     */
    public void displayCharacterStats() {
        if (character == null) {
            return;
        }
        
        System.out.println("\n=== " + character.getName() + " ===");
        System.out.println("Nivel: " + character.getLevel());
        System.out.println("Salud: " + character.getCurrentHealth() + "/" + character.getMaxHealth());
        System.out.println("Maná: " + character.getCurrentMana() + "/" + character.getMaxMana());
        System.out.println("Elemento: " + character.getElement());
        
        // Estadísticas adicionales según el tipo de personaje
        if (character instanceof model.characters.PhysicalCharacter) {
            model.characters.PhysicalCharacter physChar = (model.characters.PhysicalCharacter) character;
            System.out.println("Fuerza: " + physChar.getStrength());
            System.out.println("Resistencia: " + physChar.getResistance());
        } else if (character instanceof model.characters.MagicalCharacter) {
            model.characters.MagicalCharacter magicChar = (model.characters.MagicalCharacter) character;
            System.out.println("Inteligencia: " + magicChar.getIntelligence());
            System.out.println("Sabiduría: " + magicChar.getWisdom());
        }
    }
}