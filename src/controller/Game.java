package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.battle.Battle;
import model.characters.*;

public class Game {
    private ArrayList<Characters> characters;
    private Scanner scanner;
    
    public Game() {
        this.characters = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
    
    public void addCharacter(Characters character) {
        characters.add(character);
    }
    
    public void showMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Ver personajes disponibles");
            System.out.println("2. Iniciar batalla");
            System.out.println("3. Crear nuevo personaje");
            System.out.println("0. Salir");
            System.out.print("\nElige una opción: ");
            
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                
                switch (option) {
                    case 1: showCharacterList(); break;
                    case 2: startBattle(); break;
                    case 3: createNewCharacter(); break;
                    case 0: exit = true; break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingresa un número válido");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
    
    private void showCharacterList() {
        System.out.println("\n===== PERSONAJES DISPONIBLES =====");
        if (characters.isEmpty()) {
            System.out.println("No hay personajes disponibles.");
            return;
        }
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + ". " + characters.get(i));
        }
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    private void startBattle() {
        if (characters.size() < 2) {
            System.out.println("Necesitas al menos 2 personajes para iniciar una batalla.");
            return;
        }
        
        System.out.println("\nSelecciona el primer personaje:");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + ". " + characters.get(i).getName());
        }
        System.out.print("Opción: ");
        int option1 = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nSelecciona el segundo personaje:");
        for (int i = 0; i < characters.size(); i++) {
            if (i != option1 - 1) {
                System.out.println((i + 1) + ". " + characters.get(i).getName());
            }
        }
        System.out.print("Opción: ");
        int option2 = scanner.nextInt();
        scanner.nextLine();
        
        Characters player1 = characters.get(option1 - 1);
        Characters player2 = characters.get(option2 - 1);
        
        Battle battle = new Battle(player1, player2);
        battle.startBattle(scanner); // Pasamos el scanner existente
    }
    
    private void createNewCharacter() {
        System.out.println("\n===== CREAR NUEVO PERSONAJE =====");
        System.out.println("Selecciona la clase:");
        System.out.println("1. Guerrero");
        System.out.println("2. Mago");
        System.out.println("3. Arquero");
        System.out.println("4. Hechicero");
        System.out.println("5. Asesino");
        System.out.print("Opción: ");
        int classOption = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Nombre del personaje: ");
        String name = scanner.nextLine();
        
        System.out.print("Nivel (1-10): ");
        int level = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nSelecciona un elemento:");
        System.out.println("1. Fuego");
        System.out.println("2. Agua");
        System.out.println("3. Hielo");
        System.out.println("4. Eléctrico");
        System.out.println("5. Viento");
        System.out.println("6. Planta");
        System.out.println("7. Tierra");
        System.out.print("Opción: ");
        int elemOption = scanner.nextInt();
        scanner.nextLine();
        
        String elementStr;
        switch (elemOption) {
            case 1: elementStr = "fire"; break;
            case 2: elementStr = "water"; break;
            case 3: elementStr = "ice"; break;
            case 4: elementStr = "electric"; break;
            case 5: elementStr = "wind"; break;
            case 6: elementStr = "plant"; break;
            case 7: elementStr = "earth"; break;
            default: elementStr = "none";
        }
        
        Characters newCharacter = null;
        switch (classOption) {
            case 1: newCharacter = new Warrior(name, level); break;
            case 2: newCharacter = new Mage(name, level); break;
            case 3: newCharacter = new Archer(name, level); break;
            case 4: newCharacter = new Sorcerer(name, level); break;
            case 5: newCharacter = new Assassin(name, level); break;
        }
        
        if (newCharacter != null) {
            newCharacter.setElement(elementStr);
            characters.add(newCharacter);
            System.out.println("\n¡Personaje " + name + " creado con éxito!");
        } else {
            System.out.println("\nError al crear el personaje.");
        }
    }
}