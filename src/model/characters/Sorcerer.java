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
        System.out.println(getName() + " attacks with dark energy! Dealing " + damage + " damage!");
        return damage;
    }
    @Override
    public int castSpell(Character target) {
        if (getCurrentMana() < 25) {
            System.out.println(getName() + " doesn't have enough mana to cast a fire spell!");
            return 0;
        }
        useMana(25);
        int damage = calculateMagicalDamage() + (concentration * 2);
        System.out.println(getName() + " casts a powerful fire spell! Dealing " + damage + " damage!");
        return damage;
    }
    /**
    Método especial del hechicero: invoca una entidad
    */
    public int summonEntity() {
        if (getCurrentMana() < 40) {
            System.out.println(getName() + " doesn't have enough mana to summon an entity!");
            return 0;
        }
        useMana(40);
        int damage = summonPower + intelligence;
        System.out.println(getName() + " summons a fire elemental! The entity attacks for " + damage + " damage!");
        return damage;
    }
    @Override
    public int defend() {
        if (getCurrentMana() < 15) {
            System.out.println(getName() + " doesn't have enough mana to create a magical barrier!");
            return 0;
        }
        useMana(15);
        int defenseBonus = intelligence / 2;
        System.out.println(getName() + " creates a magical barrier! DEF +" + defenseBonus);
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