package view;

import model.battle.Battle;
import model.characters.Character;
import model.battle.Element;

import java.util.Scanner;

/**
 * Interfaz principal para mostrar la batalla y recoger las acciones del usuario
 */
public class BattleInterface {
    private Scanner scanner;
    private CharacterPanel player1Panel;
    private CharacterPanel player2Panel;
    private ActionMenu actionMenu;
    
    public BattleInterface() {
        this.scanner = new Scanner(System.in);
        this.player1Panel = new CharacterPanel();
        this.player2Panel = new CharacterPanel();
        this.actionMenu = new ActionMenu();
    }
    
    /**
     * Inicializa la interfaz con los personajes de la batalla
     */
    public void initialize(Character player1, Character player2) {
        player1Panel.setCharacter(player1);
        player2Panel.setCharacter(player2);
    }
    
    /**
     * Muestra el estado actual de la batalla
     */
    public void showBattleStatus(Battle battle) {
        clearScreen();
        System.out.println("\n=== TURNO " + battle.getTurnNumber() + " - " + battle.getCurrentTurn().getName() + " ===");
        
        player1Panel.displayCharacterStatus();
        System.out.println();
        player2Panel.displayCharacterStatus();
        System.out.println();
    }
    
    /**
     * Muestra un mensaje en la interfaz
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    /**
     * Muestra el resultado final de la batalla
     */
    public void showBattleResult(Battle battle) {
        clearScreen();
        System.out.println("\n=== FIN DE LA BATALLA ===");
        
        Character player1 = battle.getPlayer1();
        Character player2 = battle.getPlayer2();
        
        if (!player1.isAlive() && !player2.isAlive()) {
            System.out.println("¡EMPATE! Ambos jugadores han caído.");
        } else if (!player1.isAlive()) {
            System.out.println(player2.getName() + " ¡GANA LA BATALLA!");
        } else {
            System.out.println(player1.getName() + " ¡GANA LA BATALLA!");
        }
        
        System.out.println("\nESTADÍSTICAS FINALES:");
        System.out.println(player1.getName() + ": " + player1.getCurrentHealth() + "/" + player1.getMaxHealth() + " HP");
        System.out.println(player2.getName() + ": " + player2.getCurrentHealth() + "/" + player2.getMaxHealth() + " HP");
        System.out.println("Duración: " + (battle.getTurnNumber() - 1) + " turnos");
        
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Muestra el menú de acciones y espera la selección del usuario
     */
    public int showActionMenu(Character character) {
        return actionMenu.displayAndGetAction(character, scanner);
    }
    
    /**
     * Método para limpiar la pantalla de la consola
     */
    private void clearScreen() {
        // Intenta limpiar la consola (en sistemas que lo soporten)
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si falla, imprimir líneas en blanco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Espera a que el usuario presione ENTER
     */
    public void waitForEnter() {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Cierra los recursos de la interfaz
     */
    public void close() {
        scanner.close();
    }
}