package model.interfaces;

import model.characters.Character;

public interface Healable {
    /**
    Cura al objetivo especificado
    @param target Personaje objetivo para curar
    */
    void heal(Character target);
}