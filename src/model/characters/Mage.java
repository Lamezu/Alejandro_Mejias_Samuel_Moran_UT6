package model.characters;

import model.interfaces.Healable;
import model.interfaces.Magical;

public class Mage extends MagicalCharacter implements Magical, Healable {
    private int manaRegen;
    public Mage(String name, int level) {
        super(name, level);
        this.manaRegen = 3 + level;
        this.element = "agua";
    }
    @Override
    public int attack() {
        int damage = calculateMagicalDamage() / 2;
        System.out.println(getName() + " attacks with a staff! Dealing " + damage + " damage!");
        return damage;
    }
    @Override
    public int castSpell(Characters target) {
        if (getCurrentMana() < 20) {
            System.out.println(getName() + " doesn't have enough mana to cast a spell!");
            return 0;
        }
        useMana(20);
        int damage = calculateMagicalDamage();
        System.out.println(getName() + " casts a powerful water spell! Dealing " + damage + " damage!");
        return damage;
    }
    @Override
    public void heal(Characters target) {
        if (getCurrentMana() < 25) {
            System.out.println(getName() + " doesn't have enough mana to cast healing magic!");
            return;
        }
        useMana(25);
        int healAmount = intelligence + wisdom/2;
        target.recoverHealth(healAmount);
        System.out.println(getName() + " heals " + target.getName() + " for " + healAmount + " points!");
    }
    /**
    Método especial del mago: regenera maná
    */
    public void regenerateMana() {
        int amount = manaRegen + wisdom/3;
        recoverMana(amount);
        System.out.println(getName() + " focuses and regenerates " + amount + " mana points!");
    }
    public int getManaRegen() {
        return manaRegen;
    }
    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }
    @Override
    public String toString() {
        return super.toString() + " - Mana Regen: " + manaRegen;
    }
}