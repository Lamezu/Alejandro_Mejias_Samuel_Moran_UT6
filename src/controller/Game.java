package controller;

import model.characters.*;
import view.BattleInterface;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controlador principal del juego
 */
public class Game {
    private ArrayList<Characters> characters;
    private BattleInterface gameView;
    private Scanner scanner;
    
    public Game() {
        this.characters = new ArrayList<>();
        this.gameView = new BattleInterface();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Agrega un personaje a la lista del juego
     */
    public void addCharacter(Characters character) {
        characters.add(character);
    }
    
    /**
     * Muestra el menú principal del juego
     */
    public void showMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Ver personajes disponibles");
            System.out.println("2. Iniciar batalla");
            System.out.println("3. Crear nuevo personaje");
            System.out.println("0. Salir");
            
            System.out.print("\nElige una opción: ");
            int option = -1;
            
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (Exception e) {
                scanner.nextLine(); // Limpiar el buffer
                System.out.println("Opción inválida. Introduce un número.");
                continue;
            }
            
            switch (option) {
                case 1:
                    showCharacterList();
                    break;
                case 2:
                    startBattle();
                    break;
                case 3:
                    createNewCharacter();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
        
        System.out.println("¡Gracias por jugar!");
        scanner.close();
        gameView.close();
    }
    
    /**
     * Muestra la lista de personajes disponibles
     */
    private void showCharacterList() {
        System.out.println("\n===== PERSONAJES DISPONIBLES =====");
        
        if (characters.isEmpty()) {
            System.out.println("No hay personajes disponibles.");
            return;
        }
        
        for (int i = 0; i < characters.size(); i++) {
            Characters character = characters.get(i);
            System.out.println((i + 1) + ". " + character.getName() + " - Nivel " + character.getLevel());
        }
        
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Inicia una batalla entre dos personajes seleccionados
     */
    private void startBattle() {
        if (characters.size() < 2) {
            System.out.println("Necesitas al menos 2 personajes para iniciar una batalla.");
            return;
        }
        
        // Seleccionar el primer personaje
        System.out.println("\nSelecciona el primer personaje:");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + ". " + characters.get(i).getName());
        }
        
        System.out.print("Opción: ");
        int option1 = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        if (option1 < 1 || option1 > characters.size()) {
            System.out.println("Selección inválida.");
            return;
        }
        
        // Seleccionar el segundo personaje
        System.out.println("\nSelecciona el segundo personaje:");
        for (int i = 0; i < characters.size(); i++) {
            if (i != option1 - 1) { // Evitar mostrar el mismo personaje
                System.out.println((i + 1) + ". " + characters.get(i).getName());
            }
        }
        
        System.out.print("Opción: ");
        int option2 = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        if (option2 < 1 || option2 > characters.size() || option1 == option2) {
            System.out.println("Selección inválida.");
            return;
        }
        
        Characters player1 = characters.get(option1 - 1);
        Characters player2 = characters.get(option2 - 1);
        
        BattleController battleController = new BattleController(player1, player2, gameView);
        battleController.startBattle();
    }
    
    /**
     * Crea un nuevo personaje
     */
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
        scanner.nextLine(); // Consumir el salto de línea
        
        if (classOption < 1 || classOption > 5) {
            System.out.println("Opción inválida.");
            return;
        }
        
        System.out.print("Nombre del personaje: ");
        String name = scanner.nextLine();
        
        System.out.print("Nivel (1-10): ");
        int level = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        if (level < 1 || level > 10) {
            level = 1;
            System.out.println("Nivel inválido. Se utilizará nivel 1.");
        }
        
        // Seleccionar elemento
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
        scanner.nextLine(); // Consumir el salto de línea
        
        Element element;
        switch (elemOption) {
            case 1: element = Element.FIRE; break;
            case 2: element = Element.WATER; break;
            case 3: element = Element.ICE; break;
            case 4: element = Element.ELECTRIC; break;
            case 5: element = Element.WIND; break;
            case 6: element = Element.PLANT; break;
            case 7: element = Element.EARTH; break;
            default: element = Element.NONE;
        }
        
        Characters newCharacter = null;
        
        switch (classOption) {
            case 1:
                newCharacter = new Warrior(name, level, element);
                break;
            case 2:
                newCharacter = new Mage(name, level, element);
                break;
            case 3:
                newCharacter = new Archer(name, level, element);
                break;
            case 4:
                newCharacter = new Sorcerer(name, level, element);
                break;
            case 5:
                newCharacter = new Assassin(name, level, element);
                break;
        }
        
        if (newCharacter != null) {
            characters.add(newCharacter);
            System.out.println("\n¡Personaje " + name + " creado con éxito!");
        } else {
            System.out.println("\nError al crear el personaje.");
        }
    }
    
    /**
     * Muestra las acciones de todos los personajes
     */
    public void showActions() {
        System.out.println("\n===== ACCIONES DE PERSONAJES =====");
        
        for (Characters character : characters) {
            System.out.println("\n" + character.getName() + ":");
            System.out.println("- Ataque: " + character.attack());
            
            // Usar instanceof para ejecutar habilidades específicas
            if (character instanceof model.interfaces.Defendable) {
                System.out.println("- Defensa: " + ((model.interfaces.Defendable) character).defend());
            }
            
            if (character instanceof model.interfaces.Magical) {
                System.out.println("- Hechizo: " + ((model.interfaces.Magical) character).castSpell());
            }
            
            if (character instanceof model.interfaces.Healable) {
                System.out.println("- Curación: " + ((model.interfaces.Healable) character).heal());
            }
            
            if (character instanceof Warrior) {
                System.out.println("- Ataque Cargado: " + ((Warrior) character).chargeAttack());
            } else if (character instanceof Mage) {
                System.out.println("- Regenerar Maná: " + ((Mage) character).regenerateMana());
            } else if (character instanceof Archer) {
                System.out.println("- Disparar Flecha: " + ((Archer) character).shootArrow());
            } else if (character instanceof Sorcerer) {
                System.out.println("- Invocar Entidad: " + ((Sorcerer) character).summonEntity());
            } else if (character instanceof Assassin) {
                System.out.println("- Ataque por la Espalda: " + ((Assassin) character).backstabAttack());
            }
        }
        
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
}