package model.battle;

import model.character.Character;
import java.util.Scanner;

public class Battle {
    private Character player1;
    private Character player2;
    private Character currentTurn;
    private int turnNumber;
    
    public Battle(Character player1, Character player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentTurn = player1; // Player 1 starts
        this.turnNumber = 1;
    }
    
    /**
     * Inicia la batalla por turnos
     */
    public void startBattle() {
        Scanner scanner = new Scanner(System.in);
        boolean battleOngoing = true;
        
        System.out.println("=== THE BATTLE BEGINS ===");
        System.out.println(player1.getName() + " VS " + player2.getName());
        
        // Bucle principal de la batalla
        while (battleOngoing) {
            showBattleStatus();
            
            Character opponent = (currentTurn == player1) ? player2 : player1;
            
            showActionMenu();
            
            int option = scanner.nextInt();
            performAction(option, opponent);
            
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
        System.out.println("\n=== TURN " + turnNumber + " - " + currentTurn.getName() + " ===");
        
        System.out.println(player1.getName() + " (HP: " + player1.getCurrentHealth() + "/" + player1.getMaxHealth() +
                         ", Mana: " + player1.getCurrentMana() + "/" + player1.getMaxMana() + ")");
        showProgressBar("HP", player1.getCurrentHealth(), player1.getMaxHealth(), 20);
        showProgressBar("Mana", player1.getCurrentMana(), player1.getMaxMana(), 20);
        
        if (player1.getActiveElement() != null) {
            System.out.println("Active Element: " + player1.getActiveElement());
        }
        
        System.out.println();
        
        System.out.println(player2.getName() + " (HP: " + player2.getCurrentHealth() + "/" + player2.getMaxHealth() +
                         ", Mana: " + player2.getCurrentMana() + "/" + player2.getMaxMana() + ")");
        showProgressBar("HP", player2.getCurrentHealth(), player2.getMaxHealth(), 20);
        showProgressBar("Mana", player2.getCurrentMana(), player2.getMaxMana(), 20);
        
        if (player2.getActiveElement() != null) {
            System.out.println("Active Element: " + player2.getActiveElement());
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
            startColor = "\u001B[32m"; // Green for HP
        } else if (label.equals("Mana")) {
            startColor = "\u001B[34m"; // Blue for Mana
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
        System.out.println("\nAVAILABLE ACTIONS:");
        System.out.println("1. Normal Attack");
        System.out.println("2. Defend");
        
        if (currentTurn instanceof model.interfaces.Magical) {
            System.out.println("3. Cast Spell");
        }
        
        if (currentTurn instanceof model.interfaces.Healable) {
            System.out.println("4. Heal");
        }
        
        if (currentTurn instanceof model.Character.Warrior) {
            System.out.println("5. Charge Attack");
        } else if (currentTurn instanceof model.Character.Mage) {
            System.out.println("5. Regenerate Mana");
        }
        
        System.out.print("Choose an option: ");
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
                if (currentTurn instanceof model.interfaces.Defendable) {
                    int defense = ((model.interfaces.Defendable) currentTurn).defend();
                    System.out.println(currentTurn.getName() + " defends, increasing resistance by " + defense + "!");
                } else {
                    System.out.println(currentTurn.getName() + " tries to defend but doesn't know how!");
                }
                break;
                
            case 3:
                if (currentTurn instanceof model.interfaces.Magical) {
                    damage = ((model.interfaces.Magical) currentTurn).castSpell(target);
                    if (damage > 0) {
                        applyDamage(damage, target);
                    }
                } else {
                    System.out.println("Invalid option!");
                }
                break;
                
            case 4:
                if (currentTurn instanceof model.interfaces.Healable) {
                    ((model.interfaces.Healable) currentTurn).heal(currentTurn);
                } else {
                    System.out.println("Invalid option!");
                }
                break;
                
            case 5:
                if (currentTurn instanceof model.Character.Warrior) {
                    damage = ((model.Character.Warrior) currentTurn).chargeAttack();
                    if (damage > 0) {
                        applyDamage(damage, target);
                    }
                } else if (currentTurn instanceof model.Character.Mage) {
                    ((model.Character.Mage) currentTurn).regenerateMana();
                } else {
                    System.out.println("Invalid option!");
                }
                break;
                
            default:
                System.out.println("Invalid option! You lose your turn.");
                break;
        }
    }
    
    /**
     * Aplica daño a un objetivo considerando elementos y reacciones
     */
    private void applyDamage(int baseDamage, Character target) {
        double reactionMultiplier = 1.0;
        String reactionMessage = "";
        
        if (currentTurn.getElement() != null && target.getActiveElement() != null) {
            reactionMultiplier = ElementSystem.calculateReactionMultiplier(
                currentTurn.getElement(), target.getActiveElement());
            
            reactionMessage = ElementSystem.getReactionName(
                currentTurn.getElement(), target.getActiveElement());
        }
        
        int finalDamage = (int)(baseDamage * reactionMultiplier);
        
        System.out.println(currentTurn.getName() + " deals " + baseDamage + " damage to " + target.getName());
        
        if (reactionMultiplier != 1.0) {
            System.out.println("!" + reactionMessage + "! Damage multiplier: x" + reactionMultiplier);
            System.out.println("Final damage: " + finalDamage);
        }
        
        target.receiveDamage(finalDamage);
        target.applyElement(currentTurn.getElement());
    }
    
    /**
     * Cambia el turno al siguiente jugador
     */
    private void switchTurn() {
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
     * Muestra el resultado final de la batalla
     */
    private void showBattleResult() {
        System.out.println("\n=== END OF BATTLE ===");
        
        if (!player1.isAlive() && !player2.isAlive()) {
            System.out.println("DRAW! Both players have fallen.");
        } else if (!player1.isAlive()) {
            System.out.println(player2.getName() + " WINS THE BATTLE!");
        } else {
            System.out.println(player1.getName() + " WINS THE BATTLE!");
        }
        
        System.out.println("\nFINAL STATS:");
        System.out.println(player1.getName() + ": " + player1.getCurrentHealth() + "/" + player1.getMaxHealth() + " HP");
        System.out.println(player2.getName() + ": " + player2.getCurrentHealth() + "/" + player2.getMaxHealth() + " HP");
        System.out.println("Duration: " + (turnNumber - 1) + " turns");
    }
}
