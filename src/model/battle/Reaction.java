package model.battle;

import model.characters.Characters;

public class Reaction {
    public static int calculateDamageWithReaction(Characters attacker, Characters defender, int baseDamage) {
        Element attackerElement = getElementFromString(attacker.getElement());
        Element defenderElement = getElementFromString(defender.getActiveElement());

        if (attackerElement == null || defenderElement == null) {
            return baseDamage;
        }

        double multiplier = calculateMultiplier(attackerElement, defenderElement);
        String reactionName = getReactionName(attackerElement, defenderElement);

        if (multiplier != 1.0) {
            System.out.println("\n¡REACCIÓN ELEMENTAL! **" + reactionName + "** - Multiplicador: x" + multiplier);
        }

        return (int)(baseDamage * multiplier);
    }

    private static double calculateMultiplier(Element attacker, Element defender) {
        return Element.calculateReactionMultiplier(attacker, defender);
    }

    private static String getReactionName(Element attacker, Element defender) {
        return Element.getReactionName(attacker, defender);
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