package model.characters;

import model.interfaces.Defendable;
import model.interfaces.Magical;

public class Sorcerer extends MagicalCharacter implements Magical, Defendable {
    private int concentration;
    private int summonPower;
    public Sorcerer(String name, int level) {
        super(name, level);
        this.concentration = 5 + level;
        this.summonPower = 10 + (level * 3);
        this.element = "fuego";
    }
    @Override
    public int attack() {
        int damage = calculateMagicalDamage() / 2;
        System.out.println(getName() + " ataca con energía oscura! Inflige " + damage + " de daño!");
        return damage;
    }
    @Override
    public int castSpell(Characters target) {
        if (getCurrentMana() < 25) {
            System.out.println(getName() + " no tiene suficiente maná para lanzar un hechizo de fuego!");
            return 0;
        }
        useMana(25);
        int damage = calculateMagicalDamage() + (concentration * 2);
        System.out.println(getName() + " lanza un poderoso hechizo de fuego! Inflige " + damage + " de daño!");
        return damage;
    }
    /**
    Método especial del hechicero: invoca una entidad
    */
    public int summonEntity() {
        if (getCurrentMana() < 40) {
            System.out.println(getName() + " no tiene suficiente maná para invocar una entidad!");
            return 0;
        }
        useMana(40);
        int damage = summonPower + intelligence;
        System.out.println(getName() + " invoca un elemental de fuego! La entidad ataca e inflige " + damage + " de daño!");
        return damage;
    }
    @Override
    public int defend() {
        if (getCurrentMana() < 15) {
            System.out.println(getName() + " no tiene suficiente maná para crear una barrera mágica!");
            return 0;
        }
        useMana(15);
        int defenseBonus = intelligence / 2;
        System.out.println(getName() + " crea una barrera mágica! DEF +" + defenseBonus);
        return defenseBonus;
    }
    public int getConcentration() {
        return concentration;
    }
    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }
    public int getSummonPower() {
        return summonPower;
    }
    public void setSummonPower(int summonPower) {
        this.summonPower = summonPower;
    }
    @Override
    public String toString() {
        return super.toString() + " - Concentration: " + concentration + " - Summon Power: " + summonPower;
    }
}