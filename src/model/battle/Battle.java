package model.battle;

import model.characters.Character;
import model.interfaces.Defendable;
import model.interfaces.Healable;
import model.interfaces.Magical;
import model.characters.Warrior;
import model.characters.Mage;
import java.util.Scanner;

public class Battle {
    private Character player1;
    private Character player2;
    private Character currentTurn;
    private int turnNumber;
    private int player1PenaltyTurns;
    private int player2PenaltyTurns;
    
    public Battle(Character player1, Character player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = player1; // Player 1 starts
        this.turnNumber = 1;
        this.player1PenaltyTurns = 0;
        this.player2PenaltyTurns = 0;
    }
    
    /**
     * Inicia la batalla por turnos
     */
    public void startBattle() {
        Scanner scanner = new Scanner(System.in);
        boolean battleOngoing = true;
        
        System.out.println("=== LA BATALLA COMIENZA ===");
        System.out.println(player1.getName() + " VS " + player2.getName());
        
        // Bucle principal de la batalla
        while (battleOngoing) {
            showBattleStatus();
            
            Character opponent = (currentTurn == player1) ? player2 : player1;
            int currentPenalty = (currentTurn == player1) ? player1PenaltyTurns : player2PenaltyTurns;
            
            if (currentPenalty > 0) {
                System.out.println(currentTurn.getName() + " está bajo penalización y no puede actuar este turno.");
                if (currentTurn == player1) {
                    player1PenaltyTurns--;
                } else {
                    player2PenaltyTurns--;
                }
            } else {
                showActionMenu();
                int option = scanner.nextInt();
                performAction(option, opponent);
            }
            
            if (!player1.isAlive() || !player2.isAlive()) {
                battleOngoing = false;
                showBattleResult();
            } else {
                switchTurn();
            }
        }
    }
    
    /**
     * Muestra el estado actual de la batalla
     */
    private void showBattleStatus() {
        System.out.println("\n=== TURNO " + turnNumber + " - " + currentTurn.getName() + " ===");
        
        System.out.println(player1.getName() + " (HP: " + player1.getCurrentHealth() + "/" + player1.getMaxHealth() +
                         ", Mana: " + player1.getCurrentMana() + "/" + player1.getMaxMana() + ")");
        showProgressBar("HP", player1.getCurrentHealth(), player1.getMaxHealth(), 20);
        showProgressBar("Mana", player1.getCurrentMana(), player1.getMaxMana(), 20);
        
        if (player1.getActiveElement() != null) {
            System.out.println("Elemento activo: " + player1.getActiveElement());
        }
        
        System.out.println();
        
        System.out.println(player2.getName() + " (HP: " + player2.getCurrentHealth() + "/" + player2.getMaxHealth() +
                         ", Mana: " + player2.getCurrentMana() + "/" + player2.getMaxMana() + ")");
        showProgressBar("HP", player2.getCurrentHealth(), player2.getMaxHealth(), 20);
        showProgressBar("Mana", player2.getCurrentMana(), player2.getMaxMana(), 20);
        
        if (player2.getActiveElement() != null) {
            System.out.println("Elemento activo: " + player2.getActiveElement());
        }
        
        System.out.println();
    }
    
    /**
     * Muestra una barra de progreso visual
     */
    private void showProgressBar(String label, int currentValue, int maxValue, int length) {
        int filled = (int) ((double) currentValue / maxValue * length);
        
        String startColor = "";
        String endColor = "";
        
        if (label.equals("HP")) {
            startColor = "\u001B[32m"; // Verde para HP
        } else if (label.equals("Mana")) {
            startColor = "\u001B[34m"; // Azul para Mana
        }
        endColor = "\u001B[0m";
        
        System.out.print(label + ": [");
        System.out.print(startColor);
        
        for (int i = 0; i < length; i++) {
            if (i < filled) {
                System.out.print("█");
            } else {
                System.out.print(" ");
            }
        }
        
        System.out.print(endColor);
        System.out.println("] " + currentValue + "/" + maxValue);
    }
    
    /**
     * Muestra el menú de acciones disponibles
     */
    private void showActionMenu() {
        System.out.println("\nACCIONES DISPONIBLES:");
        System.out.println("1. Ataque Normal");
        System.out.println("2. Defender");
        
        if (currentTurn instanceof Magical) {
            System.out.println("3. Lanzar Hechizo");
        }
        
        if (currentTurn instanceof Healable) {
            System.out.println("4. Curar");
        }
        
        if (currentTurn instanceof Warrior) {
            System.out.println("5. Ataque Cargado");
        } else if (currentTurn instanceof Mage) {
            System.out.println("5. Regenerar Maná");
        }
        
        System.out.print("Elige una opción: ");
    }
    
    /**
     * Realiza la acción seleccionada por el jugador
     */
    private void performAction(int option, Character target) {
        int damage = 0;
        
        switch (option) {
            case 1:
                damage = currentTurn.attack();
                applyDamage(damage, target);
                break;
                
            case 2:
                if (currentTurn instanceof Defendable) {
                    int defense = ((Defendable) currentTurn).defend();
                    System.out.println(currentTurn.getName() + " se defiende, aumentando su resistencia en " + defense + "!");
                } else {
                    System.out.println(currentTurn.getName() + " intenta defenderse pero no sabe cómo!");
                }
                break;
                
            case 3:
                if (currentTurn instanceof Magical) {
                    damage = ((Magical) currentTurn).castSpell(target);
                    if (damage > 0) {
                        applyDamage(damage, target);
                    }
                } else {
                    System.out.println("¡Opción inválida!");
                }
                break;
                
            case 4:
                if (currentTurn instanceof Healable) {
                    ((Healable) currentTurn).heal(currentTurn);
                } else {
                    System.out.println("¡Opción inválida!");
                }
                break;
                
            case 5:
                if (currentTurn instanceof Warrior) {
                    damage = ((Warrior) currentTurn).chargeAttack();
                    if (damage > 0) {
                        applyDamage(damage, target);
                    }
                } else if (currentTurn instanceof Mage) {
                    ((Mage) currentTurn).regenerateMana();
                } else {
                    System.out.println("¡Opción inválida!");
                }
                break;
                
            default:
                System.out.println("¡Opción inválida! Pierdes tu turno.");
                break;
        }
    }
    
    /**
     * Aplica daño a un objetivo considerando elementos y reacciones
     */
    private void applyDamage(int baseDamage, Character target) {
        int finalDamage = Reaction.calculateDamageWithReaction(currentTurn, target, baseDamage);
        target.receiveDamage(finalDamage);
        System.out.println(currentTurn.getName() + " inflige " + finalDamage + " daño a " + target.getName());
    }
    
    /**
     * Cambia el turno al siguiente jugador
     */
    public void switchTurn() {
        player1.updateElementStatus();
        player2.updateElementStatus();
        
        if (currentTurn == player1) {
            currentTurn = player2;
        } else {
            currentTurn = player1;
            turnNumber++;
        }
    }
    
    /**
     * Actualiza los turnos de penalización para un jugador
     */
    public void applyPenaltyToPlayer(Character player, int turns) {
        if (player == player1) {
            player1PenaltyTurns = turns;
        } else if (player == player2) {
            player2PenaltyTurns = turns;
        }
    }
    
    /**
     * Muestra el resultado final de la batalla
     */
    private void showBattleResult() {
        System.out.println("\n=== FIN DE LA BATALLA ===");
        
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
        System.out.println("Duración: " + (turnNumber - 1) + " turnos");
    }
    
    // Getters
    public Character getPlayer1() {
        return player1;
    }
    
    public Character getPlayer2() {
        return player2;
    }
    
    public Character getCurrentTurn() {
        return currentTurn;
    }
    
    public int getTurnNumber() {
        return turnNumber;
    }
}