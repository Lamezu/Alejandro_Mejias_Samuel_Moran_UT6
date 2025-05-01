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
            System.out.println(getName() + " realiza un GOLPE CRÍTICO! Inflige " + damage + " de daño!");
        } else {
            System.out.println(getName() + " ataca con dagas! Inflige " + damage + " de daño!");
        }
        return damage;
    }
    /**
    Método especial del asesino: ataque por la espalda
    */
    public int backstabAttack() {
        if (getCurrentMana() < 15) {
            System.out.println(getName() + " no tiene suficiente energía para realizar un ataque por la espalda!");
            return 0;
        }
        useMana(15);
        int baseDamage = calculatePhysicalDamage();
        int damage = baseDamage + (stealth / 2);
        System.out.println(getName() + " realiza un ataque por la espalda! Inflige " + damage + " de daño!");
        return damage;
    }
    /**
    Método para ocultarse
    */
    public void hide() {
        if (getCurrentMana() < 10) {
            System.out.println(getName() + " no tiene suficiente energía para esconderse!");
            return;
        }
        useMana(10);
        criticalChance += 15;
        System.out.println(getName() + " se oculta en las sombras! La probabilidad de crítico aumenta a " + criticalChance + "%!");
    }
    @Override
    public void move() {
        System.out.println(getName() + " se mueve silenciosamente con " + stealth + " de sigilo!");
    }
    @Override
    public void heal(Characters target) {
        if (getCurrentMana() < 20) {
            System.out.println(getName() + " no tiene suficiente energía para usar medicina!");
            return;
        }
        useMana(20);
        int healAmount = getLevel() * 5;
        target.recoverHealth(healAmount);
        System.out.println(getName() + " usa medicina rápida en " + target.getName() + " por " + healAmount + " de salud!");
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