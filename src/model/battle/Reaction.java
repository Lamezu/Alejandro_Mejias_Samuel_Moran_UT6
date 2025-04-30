package model.battle;

import model.characters.Characters;

public class Reaction {
    public static int calculateDamageWithReaction(Characters attacker, Characters defender, int baseDamage) {
        Element attackerElement = getElementFromString(attacker.getElement());
        Element defenderElement = getElementFromString(defender.getActiveElement());
        
        if (attackerElement == null || defenderElement == null) {
            return baseDamage;
        }
        
        double multiplier = Element.calculateReactionMultiplier(attackerElement, defenderElement);
        String reactionName = Element.getReactionName(attackerElement, defenderElement);
        
        if (multiplier != 1.0) {
            System.out.println("¡" + reactionName + "! Multiplicador de daño: x" + multiplier);
        }
        
        return (int)(baseDamage * multiplier);
    }
    
    public static boolean hasReaction(Element attackerElement, Element defenderElement) {
        return Element.calculateReactionMultiplier(attackerElement, defenderElement) != 1.0;
    }
    
    private static Element getElementFromString(String elementStr) {
        if (elementStr == null) return null;
        try {
            return Element.valueOf(elementStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}