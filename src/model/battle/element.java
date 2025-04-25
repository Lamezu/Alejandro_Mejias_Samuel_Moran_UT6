package model.battle;

import java.util.HashMap;
import java.util.Map;

public class Element {
    // Constantes para los elementos
    public static final String FIRE = "fuego";
    public static final String WATER = "agua";
    public static final String ICE = "hielo";
    public static final String ELECTRIC = "electrico";
    public static final String WIND = "viento";
    public static final String PLANT = "planta";
    public static final String EARTH = "tierra";

    // Mapa de reacciones elementales (elemento1 + elemento2 -> multiplicador de daño)
    private static final Map<String, Map<String, Double>> reactions = new HashMap<>();

    static {
        // Inicializar el mapa de reacciones

        // Reacciones con FUEGO
        Map<String, Double> fireReactions = new HashMap<>();
        fireReactions.put(WATER, 0.5);    // Fuego + Agua = Daño reducido (apagado)
        fireReactions.put(ICE, 2.0);      // Fuego + Hielo = Daño aumentado (derretido)
        fireReactions.put(PLANT, 2.0);    // Fuego + Planta = Daño aumentado (quemado)
        fireReactions.put(WIND, 1.5);     // Fuego + Viento = Daño aumentado (propagación)
        reactions.put(FIRE, fireReactions);

        // Reacciones con AGUA
        Map<String, Double> waterReactions = new HashMap<>();
        waterReactions.put(FIRE, 2.0);       // Agua + Fuego = Daño aumentado (vapor)
        waterReactions.put(ELECTRIC, 1.5);   // Agua + Eléctrico = Daño aumentado (electrocución)
        waterReactions.put(EARTH, 1.5);      // Agua + Tierra = Daño aumentado (lodo)
        reactions.put(WATER, waterReactions);

        // Reacciones con HIELO
        Map<String, Double> iceReactions = new HashMap<>();
        iceReactions.put(FIRE, 0.5);      // Hielo + Fuego = Daño reducido (derretido)
        iceReactions.put(WATER, 1.5);     // Hielo + Agua = Daño aumentado (congelado)
        iceReactions.put(WIND, 1.5);      // Hielo + Viento = Daño aumentado (ventisca)
        reactions.put(ICE, iceReactions);

        // Reacciones con ELECTRICO
        Map<String, Double> electricReactions = new HashMap<>();
        electricReactions.put(WATER, 2.0);   // Eléctrico + Agua = Daño aumentado (electrocución)
        electricReactions.put(EARTH, 0.5);   // Eléctrico + Tierra = Daño reducido (absorbido)
        reactions.put(ELECTRIC, electricReactions);

        // Reacciones con VIENTO
        Map<String, Double> windReactions = new HashMap<>();
        windReactions.put(FIRE, 1.5);       // Viento + Fuego = Daño aumentado (propagación)
        windReactions.put(ELECTRIC, 1.5);   // Viento + Eléctrico = Daño aumentado (tormenta)
        reactions.put(WIND, windReactions);

        // Reacciones con PLANTA
        Map<String, Double> plantReactions = new HashMap<>();
        plantReactions.put(FIRE, 0.5);      // Planta + Fuego = Daño reducido (quemado)
        plantReactions.put(WATER, 1.5);     // Planta + Agua = Daño aumentado (crecimiento)
        plantReactions.put(ICE, 0.5);       // Planta + Hielo = Daño reducido (congelado)
        reactions.put(PLANT, plantReactions);

        // Reacciones con TIERRA
        Map<String, Double> earthReactions = new HashMap<>();
        earthReactions.put(WATER, 0.5);     // Tierra + Agua = Daño reducido (lodo)
        earthReactions.put(ELECTRIC, 2.0);  // Tierra + Eléctrico = Daño aumentado (absorción)
        earthReactions.put(PLANT, 0.5);     // Tierra + Planta = Daño reducido (raíces)
        reactions.put(EARTH, earthReactions);
    }

    /**
     * Calcula el multiplicador de daño basado en la reacción elemental
     * @param elementoAtacante Elemento del atacante
     * @param elementoDefensor Elemento del defensor
     * @return Multiplicador de daño
     */
    public static double calculateReactionMultiplier(String attackingElement, String defendingElement) {
        // Si alguno de los elementos es nulo, no hay reacción
        if (attackingElement == null || defendingElement == null) {
            return 1.0;
        }

        // Si ambos elementos son iguales, no hay reacción especial
        if (attackingElement.equals(defendingElement)) {
            return 1.0;
        }

        // Buscar la reacción en el mapa
        try {
            Map<String, Double> attackerReactions = reactions.get(attackingElement);
            if (attackerReactions != null && attackerReactions.containsKey(defendingElement)) {
                return attackerReactions.get(defendingElement);
            }
        } catch (Exception e) {
            System.err.println("Error while calculating elemental reaction: " + e.getMessage());
        }

        // Si no hay reacción específica
        return 1.0;
    }

    /**
     * Obtiene el nombre de la reacción entre dos elementos
     * @param elementoAtacante Elemento del atacante
     * @param elementoDefensor Elemento del defensor
     * @return Nombre de la reacción
     */
    public static String getReactionName(String attackingElement, String defendingElement) {
        if (attackingElement == null || defendingElement == null) {
            return "Sin reacción";
        }

        // Combinaciones específicas de reacciones
        if (attackingElement.equals(FIRE) && defendingElement.equals(WATER)) {
            return "¡Vaporización!";
        } else if (attackingElement.equals(WATER) && defendingElement.equals(FIRE)) {
            return "¡Extinción!";
        } else if (attackingElement.equals(FIRE) && defendingElement.equals(ICE)) {
            return "¡Derretimiento!";
        } else if (attackingElement.equals(WATER) && defendingElement.equals(ELECTRIC)) {
            return "¡Electrocución!";
        } else if (attackingElement.equals(WIND) && defendingElement.equals(FIRE)) {
            return "¡Propagación de llamas!";
        } else if (attackingElement.equals(EARTH) && defendingElement.equals(ELECTRIC)) {
            return "¡Absorción de energía!";
        }
        // Añade más combinaciones según necesites

        return "Reacción elemental";
    }
}
