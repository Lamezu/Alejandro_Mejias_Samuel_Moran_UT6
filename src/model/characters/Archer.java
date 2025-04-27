package model.characters;

import model.interfaces.Flying;
import model.interfaces.Movable;

public class Archer extends PhysicalCharacter implements Movable, Flying {
    private int agility;
    private int arrowCount;
    private final int MAX_ARROWS = 20;
    public Archer(String name, int level) {
    super(name, level);
    this.agility = 10 + (level * 2);
    this.arrowCount = MAX_ARROWS;
    this.element = "viento";
    }
    @Override
    public int attack() {
        if (arrowCount <= 0) {
            System.out.println(getName() + " is out of arrows! Need to restock!");
            return 0;
        }
        arrowCount--;
        int damage = calculatePhysicalDamage();
        System.out.println(getName() + " fires an arrow! Dealing " + damage + " damage! (" + arrowCount + " arrows left)");
        return damage;
    }
    /**
    Método especial del arquero: dispara una flecha precisa
    */
    public int shootPrecisionArrow() {
        if (arrowCount <= 0) {
            System.out.println(getName() + " is out of arrows!");
            return 0;
        }
        if (getCurrentMana() < 10) {
            System.out.println(getName() + " doesn't have enough focus to aim properly!");
            return 0;
        }
        useMana(10);
        arrowCount--;
        int damage = (int)(calculatePhysicalDamage() * (1 + agility/50.0));
        System.out.println(getName() + " shoots a precision arrow! Dealing " + damage + " damage! (" + arrowCount + " arrows left)");
        return damage;
    }
    /**
    Método para recargar flechas
    */
    public void restockArrows() {
        int previous = arrowCount;
        arrowCount = MAX_ARROWS;
        System.out.println(getName() + " restocks arrows! +" + (arrowCount - previous) + " arrows! (Total: " + arrowCount + ")");
    }
    @Override
    public void move() {
        System.out.println(getName() + " moves quickly with " + agility + " agility!");
    }
    @Override
    public void fly() {
        if (getCurrentMana() < 15) {
        System.out.println(getName() + " doesn't have enough energy to reach a high position!");
        return;
        }
        useMana(15);
        System.out.println(getName() + " uses wind energy to reach a higher position!");
    }
    public int getAgility() {
        return agility;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public int getArrowCount() {
        return arrowCount;
    }
    public void setArrowCount(int arrowCount) {
        this.arrowCount = Math.min(arrowCount, MAX_ARROWS);
    }
    @Override
        public String toString() {
        return super.toString() + " - AGI: " + agility + " - Arrows: " + arrowCount + "/" + MAX_ARROWS;
    }
}