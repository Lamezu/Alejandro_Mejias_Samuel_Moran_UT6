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
        System.out.println(getName() + " ataca con un bastón! Inflige " + damage + " de daño!");
        return damage;
    }
    @Override
    public int castSpell(Characters target) {
        if (getCurrentMana() < 20) {
            System.out.println(getName() + " no tiene suficiente maná para lanzar un hechizo!");
            return 0;
        }
        useMana(20);
        int damage = calculateMagicalDamage();
        System.out.println(getName() + " lanza un poderoso hechizo de agua! Inflige " + damage + " de daño!");
        return damage;
    }
    @Override
    public void heal(Characters target) {
        if (getCurrentMana() < 25) {
            System.out.println(getName() + " no tiene suficiente maná para lanzar magia curativa!");
            return;
        }
        useMana(25);
        int healAmount = intelligence + wisdom/2;
        target.recoverHealth(healAmount);
        System.out.println(getName() + " cura a " + target.getName() + " por " + healAmount + " puntos!");
    }
    /**
    Método especial del mago: regenera maná
    */
    public void regenerateMana() {
        int amount = manaRegen + wisdom/3;
        recoverMana(amount);
        System.out.println(getName() + " se concentra y regenera " + amount + " puntos de maná!");
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