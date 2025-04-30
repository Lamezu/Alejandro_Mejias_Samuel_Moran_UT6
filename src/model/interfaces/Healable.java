package model.interfaces;

import model.characters.Characters;

public interface Healable {
    /**
    Cura al objetivo especificado
    @param target Personaje objetivo para curar
    */
    void heal(Characters target);
}