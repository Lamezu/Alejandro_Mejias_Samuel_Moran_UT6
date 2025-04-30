package model.battle;

import model.characters.Characters;
import model.characters.PhysicalCharacter;
import model.characters.MagicalCharacter;

public class Potion {
    // Constantes para tipos de pociones
    public static final String HEALTH = "health";
    public static final String MANA = "mana";
    public static final String STRENGTH = "strength";
    public static final String INTELLIGENCE = "intelligence";
    public static final String ELEMENTAL = "elemental";
    
    private String name;
    private String type;
    private int amount;
    private int manaCost;
    private int penaltyTurns;
    private Element element; // Para pociones elementales
    
    public Potion(String name, String type, int amount, int manaCost, int penaltyTurns) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.manaCost = manaCost;
        this.penaltyTurns = penaltyTurns;
        this.element = null;
    }
    
    public Potion(String name, String type, int amount, int manaCost, int penaltyTurns, Element element) {
        this(name, type, amount, manaCost, penaltyTurns);
        this.element = element;
    }
    
    /**
     * Usa la poción en un personaje objetivo
     * @param user El personaje que usa la poción
     * @param target El personaje objetivo (puede ser el mismo)
     * @return true si se pudo usar la poción, false en caso contrario
     */
    public boolean use(Characters user, Characters target) {
        // Verificar si el usuario tiene suficiente maná
        if (user.getCurrentMana() < manaCost) {
            System.out.println(user.getName() + " no tiene suficiente maná para usar " + name);
            return false;
        }
        
        // Usar maná
        user.useMana(manaCost);
        System.out.println(user.getName() + " usa " + name + " en " + target.getName());
        
        // Aplicar efecto según el tipo
        boolean success = true;
        
        switch (type) {
            case HEALTH:
                target.recoverHealth(amount);
                System.out.println(target.getName() + " recupera " + amount + " puntos de salud");
                break;
                
            case MANA:
                target.recoverMana(amount);
                System.out.println(target.getName() + " recupera " + amount + " puntos de maná");
                break;
                
            case STRENGTH:
                if (target instanceof PhysicalCharacter) {
                    ((PhysicalCharacter) target).increaseStrength(amount);
                    System.out.println(target.getName() + " aumenta su fuerza en " + amount + " puntos");
                } else {
                    System.out.println("Este tipo de poción no afecta a " + target.getName());
                    success = false;
                }
                break;
                
            case INTELLIGENCE:
                if (target instanceof MagicalCharacter) {
                    ((MagicalCharacter) target).increaseIntelligence(amount);
                    System.out.println(target.getName() + " aumenta su inteligencia en " + amount + " puntos");
                } else {
                    System.out.println("Este tipo de poción no afecta a " + target.getName());
                    success = false;
                }
                break;
                
            case ELEMENTAL:
                if (element != null) {
                    target.applyElement(element);
                    System.out.println(target.getName() + " se imbuye con el elemento " + element.getName());
                } else {
                    System.out.println("Poción elemental inválida");
                    success = false;
                }
                break;
                
            default:
                System.out.println("Tipo de poción desconocido");
                success = false;
        }
        
        // Informar sobre la penalización
        if (success && penaltyTurns > 0) {
            user.applyPenalty(penaltyTurns);
            System.out.println(user.getName() + " no podrá atacar durante " + penaltyTurns + " turno(s)");
        }
        
        return success;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public int getManaCost() {
        return manaCost;
    }
    
    public int getPenaltyTurns() {
        return penaltyTurns;
    }
    
    public Element getElement() {
        return element;
    }
}