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
        String attackerElement = attacker.getElement();
        String defenderActiveElement = defender.getActiveElement();
        
        // Lógica de reacciones elementales
        double multiplier = calculateReactionMultiplier(attackerElement, defenderActiveElement);
        String reactionName = getReactionName(attackerElement, defenderActiveElement);
        

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
     * Calcula el multiplicador de daño basado en los elementos del atacante y el defensor.
     * @param attackingElement Elemento del atacante.
     * @param defendingElement Elemento del defensor.
     * @return Multiplicador de daño.
     */
    private static double calculateReactionMultiplier(String attackingElement, String defendingElement) {
        if (attackingElement == null || defendingElement == null || 
            attackingElement.equals("ninguno") || defendingElement.equals("ninguno")) {
            return 1.0; // Sin reacción si no hay elementos válidos
        }

        // Lógica de multiplicadores
        if (attackingElement.equals("agua") && defendingElement.equals("fuego")) {
            return 2.0; // Agua es fuerte contra fuego
        } else if (attackingElement.equals("fuego") && defendingElement.equals("agua")) {
            return 0.5; // Fuego es débil contra agua
        }  else if (attackingElement.equals("fuego") && defendingElement.equals("hielo")) {
            return 2.0; // Fuego es fuerte contra hielo
        } else if (attackingElement.equals("fuego") && defendingElement.equals("planta")) {
            return 2.0; // Fuego es fuerte contra planta
        } else if (attackingElement.equals("fuego") && defendingElement.equals("viento")) {
            return 1.5; // Fuego es moderadamente fuerte contra viento
        } else if (attackingElement.equals("agua") && defendingElement.equals("fuego")) {
            return 2.0; // Agua es fuerte contra fuego
        } else if (attackingElement.equals("agua") && defendingElement.equals("electrico")) {
            return 1.5; // Agua es moderadamente fuerte contra eléctrico
        } else if (attackingElement.equals("hielo") && defendingElement.equals("fuego")) {
            return 0.5; // Hielo es débil contra fuego
        } else if (attackingElement.equals("hielo") && defendingElement.equals("agua")) {
            return 1.5; // Hielo es moderadamente fuerte contra agua
        } else if (attackingElement.equals("hielo") && defendingElement.equals("viento")) {
            return 1.5; // Hielo es moderadamente fuerte contra viento
        } else if (attackingElement.equals("electrico") && defendingElement.equals("agua")) {
            return 2.0; // Eléctrico es fuerte contra agua
        } else if (attackingElement.equals("viento") && defendingElement.equals("fuego")) {
            return 1.5; // Viento es moderadamente fuerte contra fuego
        } else if (attackingElement.equals("viento") && defendingElement.equals("electrico")) {
            return 1.5; // Viento es moderadamente fuerte contra eléctrico
        } else if (attackingElement.equals("planta") && defendingElement.equals("fuego")) {
            return 0.5; // Planta es débil contra fuego
        } else if (attackingElement.equals("planta") && defendingElement.equals("agua")) {
            return 1.5; // Planta es moderadamente fuerte contra agua
        } else if (attackingElement.equals("planta") && defendingElement.equals("hielo")) {
            return 0.5; // Planta es débil contra hielo
        }

        // Otros casos...
        return 1.0;
    }

    /**
     * Devuelve el nombre de la reacción elemental basada en el atacante y el defensor.
     * @param attackingElement Elemento del atacante.
     * @param defendingElement Elemento del defensor.
     * @return Nombre de la reacción elemental.
     */
    private static String getReactionName(String attackingElement, String defendingElement) {
        if (attackingElement.equals("agua") && defendingElement.equals("fuego")) {
            return "¡Vaporizado!";
        } else if (attackingElement.equals("fuego") && defendingElement.equals("agua")) {
            return "¡Vaporizado inverso!";
        } else if (attackingElement.equals("fuego") && defendingElement.equals("hielo")) {
            return "¡Derretido!"; // Fuego es fuerte contra hielo
        } else if (attackingElement.equals("fuego") && defendingElement.equals("planta")) {
            return "¡Quemado!"; // Fuego es fuerte contra planta
        } else if (attackingElement.equals("fuego") && defendingElement.equals("viento")) {
            return "¡Torbellino de fuego!"; // Fuego es moderadamente fuerte contra viento
        } else if (attackingElement.equals("agua") && defendingElement.equals("electrico")) {
            return "¡Electrocargado!"; // Agua es moderadamente fuerte contra eléctrico
        } else if (attackingElement.equals("hielo") && defendingElement.equals("fuego")) {
            return "¡Derretido inverso!"; // Hielo es débil contra fuego
        } else if (attackingElement.equals("hielo") && defendingElement.equals("agua")) {
            return "¡Congelado líquido!"; // Hielo es moderadamente fuerte contra agua
        } else if (attackingElement.equals("hielo") && defendingElement.equals("viento")) {
            return "¡Torbellino helado!"; // Hielo es moderadamente fuerte contra viento
        } else if (attackingElement.equals("electrico") && defendingElement.equals("agua")) {
            return "¡Electrocargado!"; // Eléctrico es fuerte contra agua
        } else if (attackingElement.equals("viento") && defendingElement.equals("fuego")) {
            return "¡Torbellino de fuego!"; // Viento es moderadamente fuerte contra fuego
        } else if (attackingElement.equals("viento") && defendingElement.equals("electrico")) {
            return "¡Torbellino eléctrico!"; // Viento es moderadamente fuerte contra eléctrico
        } else if (attackingElement.equals("planta") && defendingElement.equals("fuego")) {
            return "¡Quemado inverso!"; // Planta es débil contra fuego
        } else if (attackingElement.equals("planta") && defendingElement.equals("agua")) {
            return "¡Nutrición vegetal!"; // Planta es moderadamente fuerte contra agua
        } else if (attackingElement.equals("planta") && defendingElement.equals("hielo")) {
            return "¡Congelación vegetal inversa!"; // Planta es débil contra hielo
        }

        return "Sin reacción";
    }
}