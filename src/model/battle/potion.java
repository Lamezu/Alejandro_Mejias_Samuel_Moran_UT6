package model.battle;

import model.character.Character;

public class Potion {
    private String name;
    private String type; // "curacion", "mana", "fuerza", etc.
    private int amount; // Cantidad de efecto
    private int manaCost; // Coste de maná para usar la poción
    private int penaltyTurns; // Turnos de penalización

    public Potion(String name, String type, int amount, int manaCost, int penaltyTurns) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.manaCost = manaCost;
        this.penaltyTurns = penaltyTurns;
    }

    /**
     * Usa la poción en un personaje objetivo
     * @param usuario El personaje que usa la poción
     * @param objetivo El personaje objetivo (puede ser el mismo)
     * @return true si se pudo usar la poción, false en caso contrario
     */
    public boolean use(Character user, Character target) {
        // Verificar si el usuario tiene suficiente maná
        if (user.getCurrentMana() < manaCost) {
            System.out.println(user.getName() + " does not have enough mana to use " + name);
            return false;
        }

        // Usar maná
        user.useMana(manaCost);
        System.out.println(user.getName() + " uses " + name + " on " + target.getName());

        // Aplicar efecto según el tipo
        switch (type) {
            case "curacion":
                target.recoverHealth(amount);
                System.out.println(target.getName() + " recovers " + amount + " health points");
                break;

            case "mana":
                target.recoverMana(amount);
                System.out.println(target.getName() + " recovers " + amount + " mana points");
                break;

            case "fuerza":
                if (target instanceof model.character.PhysicalCharacter) {
                    ((model.character.PhysicalCharacter) target).setStrength(
                        ((model.character.PhysicalCharacter) target).getStrength() + amount);
                    System.out.println(target.getName() + " increases strength by " + amount + " points");
                } else {
                    System.out.println("This type of potion does not affect " + target.getName());
                    return false;
                }
                break;

            case "inteligencia":
                if (target instanceof model.character.MagicCharacter) {
                    ((model.character.MagicCharacter) target).setIntelligence(
                        ((model.character.MagicCharacter) target).getIntelligence() + amount);
                    System.out.println(target.getName() + " increases intelligence by " + amount + " points");
                } else {
                    System.out.println("This type of potion does not affect " + target.getName());
                    return false;
                }
                break;

            default:
                System.out.println("Unknown potion type");
                return false;
        }

        // Informar sobre la penalización
        if (penaltyTurns > 0) {
            System.out.println(user.getName() + " will not be able to attack for " + penaltyTurns + " turn(s)");
        }

        return true;
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
}
