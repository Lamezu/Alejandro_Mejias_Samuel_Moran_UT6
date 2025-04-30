package view;

import model.characters.Characters;
import model.characters.Warrior;
import model.characters.Mage;
import model.characters.Archer;
import model.characters.Sorcerer;
import model.characters.Assassin;
import model.interfaces.Defendable;
import model.interfaces.Healable;
import model.interfaces.Magical;
import model.interfaces.Movable;
import model.interfaces.Flying;

import java.util.Scanner;

/**
 * Menú para mostrar y seleccionar acciones durante la batalla
 */
public class ActionMenu {
    
    /**
     * Muestra el menú de acciones disponibles según el tipo de personaje
     * @return La opción seleccionada por el usuario
     */
    public int displayAndGetAction(Characters character, Scanner scanner) {
        System.out.println("\nACCIONES DISPONIBLES:");
        System.out.println("1. Ataque Normal");
        
        int option = 2;
        
        if (character instanceof Defendable) {
            System.out.println(option + ". Defender");
            option++;
        }
        
        if (character instanceof Magical) {
            System.out.println(option + ". Lanzar Hechizo");
            option++;
        }
        
        if (character instanceof Healable) {
            System.out.println(option + ". Curar");
            option++;
        }
        
        if (character instanceof Movable) {
            System.out.println(option + ". Mover");
            option++;
        }
        
        if (character instanceof Flying) {
            System.out.println(option + ". Volar");
            option++;
        }
        
        // Habilidades específicas según la clase
        if (character instanceof Warrior) {
            System.out.println(option + ". Ataque Cargado");
            option++;
        } else if (character instanceof Mage) {
            System.out.println(option + ". Regenerar Maná");
            option++;
        } else if (character instanceof Archer) {
            System.out.println(option + ". Disparo Rápido");
            option++;
        } else if (character instanceof Sorcerer) {
            System.out.println(option + ". Invocar Criatura");
            option++;
        } else if (character instanceof Assassin) {
            System.out.println(option + ". Ataque Sigiloso");
            option++;
        }
        
        System.out.println("0. Ver estadísticas");
        
        System.out.print("\nElige una opción: ");
        int selection = -1;
        
        try {
            selection = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar el buffer
            return -1;
        }
        
        return selection;
    }
    
    /**
     * Muestra un menú para seleccionar un objetivo
     */
    public int selectTarget(Characters[] targets, Scanner scanner) {
        System.out.println("\nSelecciona un objetivo:");
        
        for (int i = 0; i < targets.length; i++) {
            System.out.println((i + 1) + ". " + targets[i].getName() + 
                            " (HP: " + targets[i].getCurrentHealth() + "/" + targets[i].getMaxHealth() + ")");
        }
        
        System.out.print("\nElige un objetivo: ");
        int selection = -1;
        
        try {
            selection = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            if (selection < 1 || selection > targets.length) {
                return -1;
            }
            
            return selection - 1;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar el buffer
            return -1;
        }
    }
}