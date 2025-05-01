package model.characters;

import model.interfaces.Defendable;

public class Warrior extends PhysicalCharacter implements Defendable {
    private int armor;
    private boolean shield;

    public Warrior(String name, int level) {
        super(name, level);
        this.armor = 5 + level;
        this.shield = true;
    }
    
    @Override
    public int attack() {
        int damage = calculatePhysicalDamage();
        System.out.println(getName() + " ataca con fuerza bruta! Inflige " + damage + " de daño!");
        return damage;
    }
    /**
    Método especial del guerrero: carga un ataque poderoso
    */
    public int chargeAttack() {
        if (getCurrentMana() < 15) {
        System.out.println(getName() + " no tiene suficiente maná para cargar un ataque!");
        return 0;
        }
        useMana(15);
        int damage = (int)(calculatePhysicalDamage() * 1.5);
        System.out.println(getName() + " carga un ataque poderoso! Inflige " + damage + " de daño!");
        return damage;
    }
    
    @Override
    public int defend() {
        int defenseBonus = resistance;
        if (shield) {
        defenseBonus += 10;
        }
        System.out.println(getName() + " adopta una postura defensiva! DEF +" + defenseBonus);
        return defenseBonus;
    }

    public int getArmor() {
        return armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public boolean hasShield() {
        return shield;
    }
    public void setShield(boolean shield) {
        this.shield = shield;
    }
    @Override
    public String toString() {
        return super.toString() + " - Armor: " + armor + (shield ? " [Shield]" : "");
    }
}