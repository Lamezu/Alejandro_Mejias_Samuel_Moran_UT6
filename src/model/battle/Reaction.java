package model.battle;

import model.characters.Characters;

public class Reaction {
    /**
     * Calcula el daño considerando las reacciones elementales.
     * @param attacker Personaje atacante
     * @param defender Personaje defensor
     * @param baseDamage Daño base del ataque
     * @return Daño final después de aplicar reacciones
     */
<<<<<<< HEAD
    public static int calculateDamageWithReaction(Character attacker, Character defender, int baseDamage) {
        Element attackerElement = attacker.getActiveElement();
=======
    public static int calculateDamageWithReaction(Characters attacker, Characters defender, int baseDamage) {
        Element attackerElement = attacker.getElement();
>>>>>>> fde1531966772f68408ecf694ef1c552bff09f0c
        Element defenderElement = defender.getActiveElement();
        
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
    
    /**
     * Verifica si hay una reacción elemental entre dos elementos
     * @param attackerElement Elemento del atacante
     * @param defenderElement Elemento del defensor
     * @return true si hay reacción, false si no
     */
    public static boolean hasReaction(Element attackerElement, Element defenderElement) {
        return Element.calculateReactionMultiplier(attackerElement, defenderElement) != 1.0;
    }
}