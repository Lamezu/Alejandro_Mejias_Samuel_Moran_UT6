package model.battle;

import model.characters.Characters;

/**
 * Sistema de elementos mejorado para manejar reacciones elementales
 */
public class ElementalSystem {
    
    /**
     * Aplica el elemento del atacante a sí mismo si no tiene uno activo
     * @param attacker Personaje atacante
     */
    public static void applyElementToAttacker(Characters attacker) {
        if (attacker.getActiveElement() == null && attacker.getElement() != null && !attacker.getElement().equals("ninguno")) {
            attacker.applyElement(attacker.getElement());
            System.out.println("✨ " + attacker.getName() + " se imbue con su propio elemento: " + attacker.getElement());
        }
    }
    
    /**
     * Aplica el elemento del atacante al defensor si no tiene uno activo
     * @param attacker Personaje atacante
     * @param defender Personaje defensor
     */
    public static void applyElementToDefender(Characters attacker, Characters defender) {
        if (defender.getActiveElement() == null && attacker.getElement() != null && !attacker.getElement().equals("ninguno")) {
            defender.applyElement(attacker.getElement());
            System.out.println("✨ " + defender.getName() + " ha sido imbuido con " + attacker.getElement() + " por " + defender.getActiveElementTurns() + " turnos!");
        }
    }
    
    /**
     * Procesa la reacción elemental entre dos personajes
     * @param attacker Personaje atacante
     * @param defender Personaje defensor
     * @param baseDamage Daño base del ataque
     * @return Daño final después de aplicar reacciones elementales
     */
    public static int processReaction(Characters attacker, Characters defender, int baseDamage) {
        // Mostrar información de elementos en juego
        System.out.println("\n⚡ Elementos en juego:");
        System.out.println("- Atacante (" + attacker.getName() + "): " + 
                         (attacker.getElement() != null ? attacker.getElement() : "Ninguno") + 
                         (attacker.getActiveElement() != null ? " [Activo: " + attacker.getActiveElement() + "]" : ""));
        System.out.println("- Objetivo (" + defender.getName() + "): " + 
                         (defender.getElement() != null ? defender.getElement() : "Ninguno") + 
                         (defender.getActiveElement() != null ? " [Activo: " + defender.getActiveElement() + "]" : ""));
                         
        // Si el defensor no tiene elemento activo, no hay reacción
        if (defender.getActiveElement() == null) {
            // Aplicar el elemento del atacante al defensor
            applyElementToDefender(attacker, defender);
            return baseDamage;
        }
        
        // Obtenemos los elementos para la reacción
        Element attackerElement = getElementFromString(attacker.getElement());
        Element defenderActiveElement = getElementFromString(defender.getActiveElement());
        
        // Si alguno de los elementos no es válido, no hay reacción
        if (attackerElement == Element.NONE || defenderActiveElement == Element.NONE) {
            return baseDamage;
        }
        
        // Si los elementos son iguales, no hay reacción pero se resetea el contador de elementos
        if (attackerElement == defenderActiveElement) {
            System.out.println("Los elementos son iguales, se refresca la duración del efecto elemental.");
            defender.setActiveElementTurns(3); // Resetear la duración del elemento
            return baseDamage;
        }
        
        // Calculamos el multiplicador de la reacción
        double multiplier = Element.calculateReactionMultiplier(attackerElement, defenderActiveElement);
        String reactionName = Element.getReactionName(attackerElement, defenderActiveElement);
        
        // Si el multiplicador es diferente de 1.0, hay una reacción
        if (multiplier != 1.0) {
            System.out.println("\n⚡¡REACCIÓN ELEMENTAL! " + reactionName + " - Multiplicador: x" + multiplier);
            int finalDamage = (int)(baseDamage * multiplier);
            System.out.println("☠️ " + defender.getName() + " sufre " + finalDamage + " puntos de daño (Base: " + 
                               baseDamage + " x " + multiplier + ")");
            
            // Limpiar el elemento activo del defensor después de la reacción
            defender.clearActiveElement();
            
            // Aplicar el nuevo elemento del atacante
            applyElementToDefender(attacker, defender);
            
            return finalDamage;
        }
        
        // Si no hay reacción especial, aplicar el elemento del atacante
        defender.clearActiveElement();
        applyElementToDefender(attacker, defender);
        
        return baseDamage;
    }
    
    /**
     * Convierte un string de elemento a su enum correspondiente
     */
    private static Element getElementFromString(String elementStr) {
        if (elementStr == null || elementStr.isEmpty()) {
            return Element.NONE;
        }
        
        try {
            return Element.valueOf(elementStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Element.NONE;
        }
    }
}