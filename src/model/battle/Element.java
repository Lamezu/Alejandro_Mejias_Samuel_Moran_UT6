package model.battle;

public enum Element {
    FIRE("fuego"),
    WATER("agua"),
    ICE("hielo"),
    ELECTRIC("electrico"),
    WIND("viento"),
    PLANT("planta"),
    NONE("ninguno");
    
    private final String name;
    
    Element(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public static double calculateReactionMultiplier(Element attackingElement, Element defendingElement) {
        if (attackingElement == null || defendingElement == null || 
            attackingElement == NONE || defendingElement == NONE) {
            return 1.0;
        }

        if (attackingElement == defendingElement) {
            return 1.0;
        }

        switch (attackingElement) {
            case FIRE:
                switch (defendingElement) {
                    case WATER: return 0.5;
                    case ICE: return 2.0;
                    case PLANT: return 2.0;
                    case WIND: return 1.5;
                    default: return 1.0;
                }
            case WATER:
                switch (defendingElement) {
                    case FIRE: return 2.0;
                    case ELECTRIC: return 1.5;
                    default: return 1.0;
                }
            case ICE:
                switch (defendingElement) {
                    case FIRE: return 0.5;
                    case WATER: return 1.5;
                    case WIND: return 1.5;
                    default: return 1.0;
                }
            case ELECTRIC:
                switch (defendingElement) {
                    case WATER: return 2.0;
                    default: return 1.0;
                }
            case WIND:
                switch (defendingElement) {
                    case FIRE: return 1.5;
                    case ELECTRIC: return 1.5;
                    default: return 1.0;
                }
            case PLANT:
                switch (defendingElement) {
                    case FIRE: return 0.5;
                    case WATER: return 1.5;
                    case ICE: return 0.5;
                    default: return 1.0;
                }
            default:
                return 1.0;
        }
    }

    public static String getReactionName(Element attackingElement, Element defendingElement) {
        if (attackingElement == null || defendingElement == null || 
            attackingElement == NONE || defendingElement == NONE) {
            return "Sin reacción";
        }

        if (attackingElement == FIRE && defendingElement == WATER) {
            return "¡Vaporizado!";
        } else if (attackingElement == WATER && defendingElement == FIRE) {
            return "¡Vaporizado!";
        } else if (attackingElement == FIRE && defendingElement == ICE) {
            return "¡Derretido!";
        } else if (attackingElement == WATER && defendingElement == ELECTRIC) {
            return "¡Electrocargado!";
        } else if (attackingElement == WIND && defendingElement == FIRE) {
            return "¡Torbellino!";
        } else if (attackingElement == WIND && defendingElement == ICE) {
            return "¡Torbellino!";
        } else if (attackingElement == WIND && defendingElement == ELECTRIC) {
            return "¡Torbellino!";
        } else if (attackingElement == PLANT && defendingElement == FIRE) {
            return "¡Quemado!";
        } else if (attackingElement == ICE && defendingElement == FIRE) {
            return "¡Derretido!";
        } else if (attackingElement == ELECTRIC && defendingElement == WATER) {
            return "¡Electrocargado!";
        } else if (attackingElement == WIND && defendingElement == PLANT) {
            return "Sin reacción";
        }

        // Si no hay reacción especial, devolver un mensaje genérico
        return "Reacción elemental";
    }
}