package model.battle;

import model.characters.Characters;

public class Reaction {
    /**
     * Calcula el daño después de aplicar reacciones elementales
     * @param attacker El personaje atacante
     * @param defender El personaje defensor
     * @param baseDamage El daño base antes de aplicar la reacción
     * @return El daño final después de aplicar la reacción elemental
     */
    public static int calculateDamageWithReaction(Characters attacker, Characters defender, int baseDamage) {
        // Primero, nos aseguramos de que el atacante tenga su propio elemento activo
        ElementalSystem.applyElementToAttacker(attacker);
        
        // Luego procesamos la reacción entre el elemento del atacante y el elemento activo del defensor
        return ElementalSystem.processReaction(attacker, defender, baseDamage);
    }

    /**
     * Procesa una reacción elemental completa, devuelve el daño y gestiona efectos secundarios
     * @param attacker El personaje atacante
     * @param defender El personaje defensor
     * @param baseDamage El daño base antes de aplicar la reacción
     * @return El daño final después de aplicar la reacción elemental
     */
    public static int processElementalReaction(Characters attacker, Characters defender, int baseDamage) {
        // Este método ahora simplemente delega al ElementalSystem
        return calculateDamageWithReaction(attacker, defender, baseDamage);
    }
    
    /**
     * Convierte un string de elemento a su enum correspondiente
     */
    public static Element getElementFromString(String elementStr) {
        if (elementStr == null || elementStr.isEmpty()) return Element.NONE;
        try {
            return Element.valueOf(elementStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Element.NONE;
        }
    }
}