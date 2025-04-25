// Reaction.java
package model.battle;

import model.character.Character;

public class Reaction {
    public static int calculateDamageWithReaction(Character attacker, Character defender, int baseDamage) {
        Element e1 = attacker.getCurrentElement();
        Element e2 = defender.getCurrentElement();

        if (e1 == Element.WATER && e2 == Element.FIRE) return (int)(baseDamage * 1.5); // Vaporizado
        if (e1 == Element.FIRE && e2 == Element.PLANT) return (int)(baseDamage * 1.5); // Quemado
        if (e1 == Element.LIGHTNING && e2 == Element.WATER) return (int)(baseDamage * 2); // Electrocución
        // ... más reacciones

        return baseDamage;
    }
}
