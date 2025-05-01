package model.battle;

import model.characters.Characters;

public class Reaction {
    public static int calculateDamageWithReaction(Characters attacker, Characters defender, int baseDamage) {
        Element attackerElement = getElementFromString(attacker.getElement());
        Element defenderElement = getElementFromString(defender.getActiveElement()); // Usar elemento activo

        // Si no hay elementos válidos, no hay reacción
        if (attackerElement == null || defenderElement == null) {
            return baseDamage; // Sin reacción
        }

        // Calcula el multiplicador y el nombre de la reacción
        double multiplier = Element.calculateReactionMultiplier(attackerElement, defenderElement);
        String reactionName = Element.getReactionName(attackerElement, defenderElement);

        // Si el multiplicador es 1.0, no hay reacción
        if (multiplier == 1.0) {
            return baseDamage; // Sin reacción
        }

        // Mostrar mensaje de reacción
        System.out.println("\n⚡¡REACCIÓN ELEMENTAL! " + reactionName + " - Multiplicador: x" + multiplier);

        // Devuelve el daño modificado
        return (int) (baseDamage * multiplier);
    }

    private static Element getElementFromString(String elementStr) {
        if (elementStr == null || elementStr.isEmpty()) return null;
        try {
            return Element.valueOf(elementStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}