package model.characters;

import model.interfaces.Healable;
import model.interfaces.Movable;

public class Assassin extends PhysicalCharacter implements Movable, Healable {
    private int stealth;
    private int criticalChance;
    public Assassin(String name, int level) {
        super(name, level);
        this.stealth = 10 + (level * 2);
        this.criticalChance = 10 + level;
        this.element = "electrico"; // Electric element
    }
    @Override
    public int attack() {
        int damage = calculatePhysicalDamage();
        // Chance for critical hit
        if (Math.random() * 100 < criticalChance) {
            damage *= 2;
            System.out.println(getName() + " lands a CRITICAL HIT! Dealing " + damage + " damage!");
        } else {
            System.out.println(getName() + " attacks with daggers! Dealing " + damage + " damage!");
        }
        return damage;
    }
    /**
    Método especial del asesino: ataque por la espalda
    */
    public int backstabAttack() {
        if (getCurrentMana() < 15) {
            System.out.println(getName() + " doesn't have enough energy to perform a backstab!");
            return 0;
        }
        useMana(15);
        int baseDamage = calculatePhysicalDamage();
        int damage = baseDamage + (stealth / 2);
        System.out.println(getName() + " performs a backstab attack! Dealing " + damage + " damage!");
        return damage;
    }
    /**
    Método para ocultarse
    */
    public void hide() {
        if (getCurrentMana() < 10) {
            System.out.println(getName() + " doesn't have enough energy to hide!");
            return;
        }
        useMana(10);
        criticalChance += 15;
        System.out.println(getName() + " hides in the shadows! Critical chance increased to " + criticalChance + "%!");
    }
    @Override
    public void move() {
        System.out.println(getName() + " moves silently with " + stealth + " stealth!");
    }
    @Override
    public void heal(Character target) {
        if (getCurrentMana() < 20) {
            System.out.println(getName() + " doesn't have enough energy to use medicine!");
            return;
        }
        useMana(20);
        int healAmount = getLevel() * 5;
        target.recoverHealth(healAmount);
        System.out.println(getName() + " uses quick medicine on " + target.getName() + " for " + healAmount + " health!");
    }
    public int getStealth() {
        return stealth;
    }
    public void setStealth(int stealth) {
        this.stealth = stealth;
    }
    public int getCriticalChance() {
        return criticalChance;
    }
    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }
    @Override
    public String toString() {
        return super.toString() + " - Stealth: " + stealth + " - Critical Chance: " + criticalChance + "%";
    }
}