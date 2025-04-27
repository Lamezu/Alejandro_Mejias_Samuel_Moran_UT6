package model.battle;

public enum Element {
    FIRE("fuego"),
    WATER("agua"),
    ICE("hielo"),
    ELECTRIC("electrico"),
    WIND("viento"),
    PLANT("planta"),
    EARTH("tierra"),
    NONE("ninguno");
    
    private final String name;
    
    Element(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Calcula el multiplicador de daño basado en la reacción elemental
     * @param attackingElement Elemento del atacante
     * @param defendingElement Elemento del defensor
     * @return Multiplicador de daño
     */
    public static double calculateReactionMultiplier(Element attackingElement, Element defendingElement) {
        // Si alguno de los elementos es nulo o NONE, no hay reacción
        if (attackingElement == null || defendingElement == null || 
            attackingElement == NONE || defendingElement == NONE) {
            return 1.0;
        }

        // Si ambos elementos son iguales, no hay reacción especial
        if (attackingElement == defendingElement) {
            return 1.0;
        }

        // Definir reacciones como pares de elementos
        switch (attackingElement) {
            case FIRE:
                switch (defendingElement) {
                    case WATER: return 0.5;    // Fuego + Agua = Daño reducido
                    case ICE: return 2.0;      // Fuego + Hielo = Daño aumentado
                    case PLANT: return 2.0;    // Fuego + Planta = Daño aumentado
                    case WIND: return 1.5;     // Fuego + Viento = Daño aumentado
                    default: return 1.0;
                }
            case WATER:
                switch (defendingElement) {
                    case FIRE: return 2.0;       // Agua + Fuego = Daño aumentado
                    case ELECTRIC: return 1.5;   // Agua + Eléctrico = Daño aumentado
                    case EARTH: return 1.5;      // Agua + Tierra = Daño aumentado
                    default: return 1.0;
                }
            case ICE:
                switch (defendingElement) {
                    case FIRE: return 0.5;      // Hielo + Fuego = Daño reducido
                    case WATER: return 1.5;     // Hielo + Agua = Daño aumentado
                    case WIND: return 1.5;      // Hielo + Viento = Daño aumentado
                    default: return 1.0;
                }
            case ELECTRIC:
                switch (defendingElement) {
                    case WATER: return 2.0;     // Eléctrico + Agua = Daño aumentado
                    case EARTH: return 0.5;     // Eléctrico + Tierra = Daño reducido
                    default: return 1.0;
                }
            case WIND:
                switch (defendingElement) {
                    case FIRE: return 1.5;      // Viento + Fuego = Daño aumentado
                    case ELECTRIC: return 1.5;  // Viento + Eléctrico = Daño aumentado
                    default: return 1.0;
                }
            case PLANT:
                switch (defendingElement) {
                    case FIRE: return 0.5;      // Planta + Fuego = Daño reducido
                    case WATER: return 1.5;     // Planta + Agua = Daño aumentado
                    case ICE: return 0.5;       // Planta + Hielo = Daño reducido
                    default: return 1.0;
                }
            case EARTH:
                switch (defendingElement) {
                    case WATER: return 0.5;     // Tierra + Agua = Daño reducido
                    case ELECTRIC: return 2.0;  // Tierra + Eléctrico = Daño aumentado
                    case PLANT: return 0.5;     // Tierra + Planta = Daño reducido
                    default: return 1.0;
                }
            default:
                return 1.0;
        }
    }

    /**
     * Obtiene el nombre de la reacción entre dos elementos
     * @param attackingElement Elemento del atacante
     * @param defendingElement Elemento del defensor
     * @return Nombre de la reacción
     */
    public static String getReactionName(Element attackingElement, Element defendingElement) {
        if (attackingElement == null || defendingElement == null || 
            attackingElement == NONE || defendingElement == NONE) {
            return "Sin reacción";
        }

        // Combinaciones específicas de reacciones
        if (attackingElement == FIRE && defendingElement == WATER) {
            return "¡Vaporización!";
        } else if (attackingElement == WATER && defendingElement == FIRE) {
            return "¡Extinción!";
        } else if (attackingElement == FIRE && defendingElement == ICE) {
            return "¡Derretimiento!";
        } else if (attackingElement == WATER && defendingElement == ELECTRIC) {
            return "¡Electrocución!";
        } else if (attackingElement == WIND && defendingElement == FIRE) {
            return "¡Propagación de llamas!";
        } else if (attackingElement == EARTH && defendingElement == ELECTRIC) {
            return "¡Absorción de energía!";
        }

        return "Reacción elemental";
    }
}