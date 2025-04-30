import model.characters.*;
import model.interfaces.*;
import controller.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== VIDEO GAME CHARACTER SYSTEM =====");

        // Crear instancia del juego
        Game game = new Game();

        // Crear personajes
        Warrior warrior = new Warrior("Aragorn", 5);
        Mage mage = new Mage("Gandalf", 5);
        Archer archer = new Archer("Legolas", 5);
        Sorcerer sorcerer = new Sorcerer("Saruman", 5);
        Assassin assassin = new Assassin("Altair", 5);

        // Agregar personajes al juego
        game.addCharacter(warrior);
        game.addCharacter(mage);
        game.addCharacter(archer);
        game.addCharacter(sorcerer);
        game.addCharacter(assassin);

        // Iniciar el juego
        game.showMainMenu();
    }
}