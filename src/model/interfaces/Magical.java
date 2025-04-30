package model.interfaces;

import model.characters.Characters;

public interface Magical {
    /**
    Lanza un hechizo contra un objetivo
    @param target Personaje objetivo del hechizo
    @return Cantidad de daño causado
    */
    int castSpell(Characters target);
}