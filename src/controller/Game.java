package controller;

import model.characters.*;
import model.battle.Element;
import view.BattleInterface;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Characters> characters;
    private BattleInterface gameView;
    private Scanner scanner;
    
    public Game() {
        this.characters = new ArrayList<>();
        this.gameView = new BattleInterface();
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
            int option = -1;
            
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
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
        
        if (option1 < 1 || option1 > characters.size()) {
            System.out.println("Selección inválida.");
            return;
        }
        
        System.out.println("\nSelecciona el segundo personaje:");
        for (int i = 0; i < characters.size(); i++) {
            if (i != option1 - 1) {
                System.out.println((i + 1) + ". " + characters.get(i).getName());
            }
        }
        
        System.out.print("Opción: ");
        int option2 = scanner.nextInt();
        scanner.nextLine();
        
        if (option2 < 1 || option2 > characters.size() || option1 == option2) {
            System.out.println("Selección inválida.");
            return;
        }
        
        Characters player1 = characters.get(option1 - 1);
        Characters player2 = characters.get(option2 - 1);
        
        BattleController battleController = new BattleController(player1, player2, gameView);
        battleController.startBattle();
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
        
        if (classOption < 1 || classOption > 5) {
            System.out.println("Opción inválida.");
            return;
        }
        
        System.out.print("Nombre del personaje: ");
        String name = scanner.nextLine();
        
        System.out.print("Nivel (1-10): ");
        int level = scanner.nextInt();
        scanner.nextLine();
        
        if (level < 1 || level > 10) {
            level = 1;
            System.out.println("Nivel inválido. Se utilizará nivel 1.");
        }
        
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
                newCharacter = new Warrior(name, level);
                break;
            case 2:
                newCharacter = new Mage(name, level);
                break;
            case 3:
                newCharacter = new Archer(name, level);
                break;
            case 4:
                newCharacter = new Sorcerer(name, level);
                break;
            case 5:
                newCharacter = new Assassin(name, level);
                break;
        }
        
        if (newCharacter != null) {
            newCharacter.setElement(element.getName().toLowerCase());
            characters.add(newCharacter);
            System.out.println("\n¡Personaje " + name + " creado con éxito!");
        } else {
            System.out.println("\nError al crear el personaje.");
        }
    }
    
    public void showActions() {
        System.out.println("\n===== ACCIONES DE PERSONAJES =====");
        
        for (Characters character : characters) {
            System.out.println("\n" + character.getName() + ":");
            System.out.println("- Ataque básico: " + character.attack());
            
            if (character instanceof model.interfaces.Defendable) {
                System.out.println("- Defensa: " + ((model.interfaces.Defendable) character).defend());
            }
            
            if (character instanceof model.interfaces.Magical) {
                System.out.println("- Hechizo: " + ((model.interfaces.Magical) character).castSpell(character));
            }
            
            if (character instanceof model.interfaces.Healable) {
                ((model.interfaces.Healable) character).heal(character);
                System.out.println("- Curación realizada");
            }
            
            if (character instanceof Warrior) {
                System.out.println("- Ataque Cargado: " + ((Warrior) character).chargeAttack());
            } else if (character instanceof Mage) {
                ((Mage) character).regenerateMana();
                System.out.println("- Maná regenerado");
            } else if (character instanceof Archer) {
                System.out.println("- Flecha precisa: " + ((Archer) character).shootPrecisionArrow());
            } else if (character instanceof Sorcerer) {
                System.out.println("- Invocación: " + ((Sorcerer) character).summonEntity());
            } else if (character instanceof Assassin) {
                System.out.println("- Ataque por la Espalda: " + ((Assassin) character).backstabAttack());
            }
        }
        
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
}