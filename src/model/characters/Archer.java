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
            System.out.println(getName() + " se quedó sin flechas! Necesita reabastecerse!");
            return 0;
        }
        arrowCount--;
        int damage = calculatePhysicalDamage();
        System.out.println(getName() + " dispara una flecha! Inflige " + damage + " de daño! (" + arrowCount + " flechas restantes)");
        return damage;
    }
    /**
    Método especial del arquero: dispara una flecha precisa
    */
    public int shootPrecisionArrow() {
        if (arrowCount <= 0) {
            System.out.println(getName() + " se quedó sin flechas!");
            return 0;
        }
        if (getCurrentMana() < 10) {
            System.out.println(getName() + " no tiene suficiente enfoque para apuntar correctamente!");
            return 0;
        }
        useMana(10);
        arrowCount--;
        int damage = (int)(calculatePhysicalDamage() * (1 + agility/50.0));
        System.out.println(getName() + " dispara una flecha precisa! Inflige " + damage + " de daño! (" + arrowCount + " flechas restantes)");
        return damage;
    }
    /**
    Método para recargar flechas
    */
    public void restockArrows() {
        int previous = arrowCount;
        arrowCount = MAX_ARROWS;
        System.out.println(getName() + " recarga flechas! +" + (arrowCount - previous) + " flechas! (Total: " + arrowCount + ")");
    }
    @Override
    public void move() {
        System.out.println(getName() + " se mueve rápidamente con " + agility + " de agilidad!");
    }
    @Override
    public void fly() {
        if (getCurrentMana() < 15) {
        System.out.println(getName() + " no tiene suficiente energía para alcanzar una posición elevada!");
        return;
        }
        useMana(15);
        System.out.println(getName() + " usa energía del viento para alcanzar una posición más alta!");
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