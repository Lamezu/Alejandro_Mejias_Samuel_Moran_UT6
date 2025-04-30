package model.util;

import java.util.Random;
import model.battle.Element;
import model.characters.MagicalCharacter;
import model.characters.PhysicalCharacter;

public class DamageCalculator {
    private static final Random random = new Random();
    
    /**
     * Calcula el daño base físico
     * @param attacker Personaje atacante
     * @return Daño base calculado
     */
    public static int calculatePhysicalDamage(PhysicalCharacter attacker) {
        int baseDamage = attacker.getStrength();
        double variationFactor = 0.9 + (random.nextDouble() * 0.2); // Entre 0.9 y 1.1
        return (int)(baseDamage * variationFactor);
    }
    
    /**
     * Calcula el daño base mágico
     * @param attacker Personaje atacante
     * @return Daño base calculado
     */
    public static int calculateMagicalDamage(MagicalCharacter attacker) {
        int baseDamage = attacker.getIntelligence();
        double variationFactor = 0.85 + (random.nextDouble() * 0.3); // Entre 0.85 y 1.15
        return (int)(baseDamage * variationFactor);
    }
    
    /**
     * Calcula si un ataque es crítico
     * @param criticalChance Probabilidad de crítico (0-100)
     * @return true si es crítico, false si no
     */
    public static boolean isCriticalHit(int criticalChance) {
        int roll = random.nextInt(100) + 1; // 1-100
        return roll <= criticalChance;
    }
    
    /**
     * Aplica daño crítico
     * @param baseDamage Daño base
     * @return Daño crítico
     */
    public static int applyCritical(int baseDamage) {
        return (int)(baseDamage * 1.5);
    }
    
    /**
     * Calcula el daño reducido por defensa
     * @param incomingDamage Daño entrante
     * @param defense Valor de defensa del receptor
     * @return Daño final después de aplicar defensa
     */
    public static int applyDefenseReduction(int incomingDamage, int defense) {
        double reductionPercentage = defense / 100.0;
        if (reductionPercentage > 0.75) reductionPercentage = 0.75; // Cap de reducción al 75%
        
        int reducedDamage = (int)(incomingDamage * (1 - reductionPercentage));
        return Math.max(1, reducedDamage); // Asegurar mínimo 1 de daño
    }
    
    /**
     * Calcula el daño considerando ventaja/desventaja elemental
     * @param baseDamage Daño base
     * @param attackerElement Elemento del atacante
     * @param defenderElement Elemento del defensor
     * @return Daño después de aplicar ventaja/desventaja elemental
     */
    public static int applyElementalAdvantage(int baseDamage, Element attackerElement, Element defenderElement) {
        double multiplier = Element.calculateReactionMultiplier(attackerElement, defenderElement);
        return (int)(baseDamage * multiplier);
    }
    
    /**
     * Calcula el tiempo de recuperación basado en la vitalidad
     * @param vitality Vitalidad del personaje
     * @return Cantidad de salud a recuperar
     */
    public static int calculateHealAmount(int vitality) {
        return vitality / 4 + random.nextInt(5);
    }
    
    /**
     * Calcula la cantidad de maná a regenerar basado en sabiduría
     * @param wisdom Sabiduría del personaje
     * @return Cantidad de maná a regenerar
     */
    public static int calculateManaRegenerationAmount(int wisdom) {
        return wisdom / 5 + random.nextInt(3);
    }
    
}